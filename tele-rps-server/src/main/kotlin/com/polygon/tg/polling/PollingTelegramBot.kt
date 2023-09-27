package com.polygon.tg.polling
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.polygon.ConfigLoader

object PollingTelegramBot {
    val pollingBot = bot {
        token = ConfigLoader.config.tgToken
        dispatch {
            text {
                bot.sendMessage(ChatId.fromId(message.chat.id), text = text)
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