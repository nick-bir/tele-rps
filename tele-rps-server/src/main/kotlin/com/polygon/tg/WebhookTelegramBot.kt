package com.polygon.tg

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.webhook
import com.polygon.ConfigLoader

object WebhookTelegramBot {
    private val webhookBot = bot {
        token = ConfigLoader.config.tgToken
        webhook {
            val domain = ConfigLoader.config.webhookDomain ?: throw Exception("Webhook URL is not configured")
            val path = ConfigLoader.config.webhookPath ?: throw Exception("Webhook URL is not configured")
            url = "$domain/$path"
        }
        dispatch(tgDispatcher)
    }

    fun startWebHook() {
        webhookBot.startWebhook()
    }

    suspend fun processUpdate(json: String) {
        webhookBot.processUpdate(json)
    }
}