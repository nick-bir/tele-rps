package com.polygon.tg.polling

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.Handler
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.Update
import com.polygon.ConfigLoader
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


object PollingTelegramBot {
    val pollingBot = bot {
        token = ConfigLoader.config.tgToken
        dispatch {
            println("dispatch")
            addHandler(LoggingHandler)
            command("start") {
                println("start")
                bot.sendMessage(
                    ChatId.fromId(message.chat.id),
                    text = TextResolver.fromCode(message.from?.languageCode).start,
                )
                update.consume()
            }
            text {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = TextResolver.fromCode(message.from?.languageCode).unknown)
                update.consume()
            }
        }
    }

    fun startPolling() {
        pollingBot.startPolling()
    }

    fun stopPolling() {
        pollingBot.stopPolling()
    }

}