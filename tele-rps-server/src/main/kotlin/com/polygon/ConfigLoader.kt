package com.polygon

import java.util.Properties

data class Config(
    val tgToken: String,
    val tgHash: String?,
    val socketTokenTimeoutSec: Int,
)

object ConfigLoader {
    val config = (javaClass.classLoader.getResourceAsStream("application.local.properties")?.use {
        Properties().apply { load(it) }
    } ?: System.getProperties()).let {
        Config(
            tgToken = it.getProperty("tg_token") ?: throw Exception("No tg token in config"),
            tgHash = it.getProperty("tg_hash"),
            socketTokenTimeoutSec = it.getProperty("socket_token_timeout_sec")?.toIntOrNull() ?: 10,
        )
    }
}