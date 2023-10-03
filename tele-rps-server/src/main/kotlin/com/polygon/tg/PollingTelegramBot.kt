package com.polygon.tg

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.handlers.Handler
import com.github.kotlintelegrambot.entities.Update
import com.polygon.ConfigLoader

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
    private val pollingBot = bot {
        token = ConfigLoader.config.tgToken
        dispatch(tgDispatcher)
    }

    fun startPolling() {
        pollingBot.startPolling()
    }

    fun stopPolling() {
        pollingBot.stopPolling()
    }

}