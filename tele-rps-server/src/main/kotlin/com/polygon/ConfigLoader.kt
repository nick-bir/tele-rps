package com.polygon

import java.util.Properties

data class Config(
    val tgToken: String
)

object ConfigLoader {
    val config = (javaClass.classLoader.getResourceAsStream("application.local.properties")?.use {
        Properties().apply { load(it) }
    } ?: System.getProperties()).let {
        Config(
            tgToken = it.getProperty("tg_token")
        )
    }
}