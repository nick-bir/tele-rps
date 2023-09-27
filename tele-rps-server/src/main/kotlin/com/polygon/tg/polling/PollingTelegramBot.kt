package com.polygon.tg.polling
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId

object PollingTelegramBot {
    val pollingBot = bot {
        token = "6592648425:AAF60dwb1uzUXFJx4bw5ej2Bfb0qRn53e4U"
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