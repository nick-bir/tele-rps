package com.polygon.tg

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandlerEnvironment
import com.github.kotlintelegrambot.dispatcher.handlers.Handler
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.polygon.ChallengeResult
import com.polygon.ConfigLoader
import com.polygon.GameController
import com.polygon.mongo.Game
import com.polygon.text.TextResolver

object LoggingHandler : Handler {
    override fun checkUpdate(update: Update): Boolean {
        println(update.toString())
        return false
    }

    override suspend fun handleUpdate(bot: Bot, update: Update) {
        println(update.toString())
    }
}

val tgDispatcher: Dispatcher.() -> Unit = {
    if (ConfigLoader.config.pollingMode) {
        addHandler(LoggingHandler)
    }
    command("start") {
        withValidUserId { userId, text ->
            val textTokens = message.text?.split(' ')
            if (textTokens?.size == 2) {
                val gameId = textTokens[1]
                val result = GameController.processChallenge(userId, gameId)
                processChallengeResult(text, result)
            } else {
                bot.sendMessage(
                    ChatId.fromId(message.chat.id),
                    text = text.start,
                )
                bot.sendMessage(
                    ChatId.fromId(message.chat.id),
                    text = TextResolver.fromCode(message.from?.languageCode).startChallenge,
                )
            }
        }
    }
    command("challenge") {
        withValidUserId { userId, text ->
            val game = GameController.createGame(userId).await()
            val link = "https://t.me/${ConfigLoader.config.tgName}?start=${game.gameId}"
            bot.sendMessage(
                    ChatId.fromId(message.chat.id),
                    text = text.play,
                    replyMarkup = InlineKeyboardMarkup.createSingleButton(InlineKeyboardButton.Url(text = text.play, url = link))
                )
            }
    }
    text {
        bot.sendMessage(ChatId.fromId(message.chat.id), text = TextResolver.fromCode(message.from?.languageCode).unknown)
        update.consume()
    }
}

private fun CommandHandlerEnvironment.processChallengeResult(text: TextResolver, result: Pair<ChallengeResult, Game?>) {
    when (result.first) {
        ChallengeResult.SAME_PLAYER -> bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = text.samePlayer
        )
        ChallengeResult.NOT_FOUND -> bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = text.samePlayer
        )
        ChallengeResult.GAME_IN_PROGRESS -> bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = text.gameInProgress
        )
        ChallengeResult.GAME_COMPLETED -> bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = text.gameCompleted
        )
        ChallengeResult.OK -> {
            val game = result.second!!
            bot.sendMessage(
                ChatId.fromId(message.chat.id),
                text = text.challengeAccepted,
            )
            bot.sendMessage(
                ChatId.fromId(game.playerId),
                text = text.challengeAccepted,
            )
        }
    }
}

typealias HandleCommandWithCtx = suspend CommandHandlerEnvironment.(userId: Long, text: TextResolver) -> Unit
suspend fun CommandHandlerEnvironment.withValidUserId(block: HandleCommandWithCtx) {
    update.consume()
    if (message.from?.id == null) {
        this.bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = TextResolver.fromCode(message.from?.languageCode).noUser,
        )
    } else {
        block(message.from!!.id, TextResolver.fromCode(message.from?.languageCode))
    }
}
