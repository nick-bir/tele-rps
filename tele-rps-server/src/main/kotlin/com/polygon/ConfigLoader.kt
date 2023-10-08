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
    val port: Int
)

object ConfigLoader {
    private val sources = listOf(
        javaClass.classLoader.getResourceAsStream("application.local.properties")?.use {
            Properties().apply { load(it) }
        },
        javaClass.classLoader.getResourceAsStream("application.properties")?.use {
            Properties().apply { load(it) }
        },
        System.getenv()
    )

    val config = Config(
            tgToken = getFromSources("tg_token"),
            tgName = getFromSources("tg_name"),
            tgHash = getNullableFromSources("tg_hash"),
            pollingMode = getNullableFromSources("polling_mode")?.toBoolean() ?: false,
            socketTokenTimeoutSec = getNullableFromSources("socket_token_timeout_sec")?.toIntOrNull() ?: 10,
            webhookPath = getNullableFromSources("webhook_path"),
            webhookDomain = getNullableFromSources("webhook_domain"),
            port = getFromSources("port").toInt()
        )

    private fun getNullableFromSources(key: String): String? {
        this.sources.forEach {
            val prop = it?.get(key)?.toString() ?: it?.get(key.uppercase())?.toString()
            if (prop != null) {
                return prop
            }
        }
        return null
    }

    private fun ConfigLoader.getFromSources(key: String): String {
        val prop = getNullableFromSources(key)
        return prop ?: throw Exception("No key $key in config")
    }
}