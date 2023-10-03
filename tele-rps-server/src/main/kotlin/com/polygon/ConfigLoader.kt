package com.polygon

import java.util.Properties

data class Config(
    val tgToken: String,
    val pollingMode: Boolean,
    val tgHash: String?,
    val socketTokenTimeoutSec: Int,
    val webhookPath: String?,
    val webhookDomain: String?,
)

object ConfigLoader {
    val config = (javaClass.classLoader.getResourceAsStream("application.local.properties")?.use {
        Properties().apply { load(it) }
    } ?: System.getProperties()).let {
        Config(
            tgToken = it.getProperty("tg_token") ?: throw Exception("No tg token in config"),
            tgHash = it.getProperty("tg_hash"),
            pollingMode = it.getProperty("polling_mode")?.toBoolean() ?: false,
            socketTokenTimeoutSec = it.getProperty("socket_token_timeout_sec")?.toIntOrNull() ?: 10,
            webhookPath = it.getProperty("webhook_path"),
            webhookDomain = it.getProperty("webhook_domain"),
        )
    }
}