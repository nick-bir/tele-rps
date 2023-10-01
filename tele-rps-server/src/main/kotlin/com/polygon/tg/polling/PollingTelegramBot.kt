package com.polygon.tg.polling

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.handlers.Handler
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.WebAppInfo
import com.polygon.ConfigLoader

object LoggingHandler: Handler {
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
            callbackQuery("start") {
                println("start")
                bot.sendMessage(
                    ChatId.fromId(callbackQuery.message!!.chat.id),
                    text = "",
                    replyMarkup = InlineKeyboardMarkup.create(listOf(InlineKeyboardButton.CallbackData(text = "hi", callbackData = "app")))
                )
            }
            command("app") {
                println("app")
                bot.sendMessage(
                    ChatId.fromId(message.chat.id),
                    text = "",
                    replyMarkup = KeyboardReplyMarkup(KeyboardButton(text = "", webApp = WebAppInfo(url = "https://nick-bir.github.io/tele-rps/index.html")))
                )
            }
            text {
                println("message: $text")
                if (text == "/app") {
                    println("app")
                    bot.sendMessage(
                        ChatId.fromId(message.chat.id),
                        text = "app",
                        replyMarkup = KeyboardReplyMarkup(KeyboardButton(text = "app", webApp = WebAppInfo(url = "https://nick-bir.github.io/tele-rps/index.html")))
                    )
                } else if (text == "/start") {
                    println("start")
                    bot.sendMessage(
                        ChatId.fromId(message.chat.id),
                        text = "hi",
                        replyMarkup = InlineKeyboardMarkup.create(listOf(InlineKeyboardButton.CallbackData(text = "hi", callbackData = "app")))
                    )
                } else {
                    bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
                }
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