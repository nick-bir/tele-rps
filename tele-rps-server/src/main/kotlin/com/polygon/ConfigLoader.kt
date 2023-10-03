package com.polygon

import java.util.Properties

data class Config(
    val tgToken: String,
    val tgName: String,
    val pollingMode: Boolean,
    val tgHash: String?,
    val socketTokenTimeoutSec: Int,
    val webhookPath: String?,
    val webhookDomain: String?,
)

object ConfigLoader {
    private val defaultProperties = javaClass.classLoader.getResourceAsStream("application.properties")?.use {
        Properties().apply { load(it) }
    } ?: System.getProperties()
    val config = (javaClass.classLoader.getResourceAsStream("application.local.properties")?.use {
        Properties().apply { load(it) }
    } ?: defaultProperties).let {
        Config(
            tgToken = it.getWithDefault("tg_token") ?: throw Exception("No tg token in config"),
            tgName = it.getWithDefault("tg_name") ?: throw Exception("No tg name in config"),
            tgHash = it.getWithDefault("tg_hash"),
            pollingMode = it.getWithDefault("polling_mode")?.toBoolean() ?: false,
            socketTokenTimeoutSec = it.getWithDefault("socket_token_timeout_sec")?.toIntOrNull() ?: 10,
            webhookPath = it.getWithDefault("webhook_path"),
            webhookDomain = it.getWithDefault("webhook_domain"),
        )
    }

    private fun Properties.getWithDefault(key: String) = this.getProperty(key) ?: defaultProperties.getProperty(key)
}