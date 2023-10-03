package com.polygon.tg

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.polygon.ConfigLoader

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