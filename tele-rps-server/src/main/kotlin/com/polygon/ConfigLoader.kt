package com.polygon

import java.util.Properties

data class MongoConfig(
    val url: String,
    val user: String?,
    val password: String?
)

data class Config(
    val tgToken: String,
    val tgName: String,
    val pollingMode: Boolean,
    val tgHash: String?,
    val socketTokenTimeoutSec: Int,
    val webhookPath: String?,
    val webhookDomain: String?,
    val port: Int,
    val mongo: MongoConfig
) {
    override fun toString(): String {
        return """
            tgToken=${tgToken.length} characters
            tgName=$tgName
            pollingMode=$pollingMode
            tgHash=$tgHash
            socketTokenTimeoutSec=$socketTokenTimeoutSec
            webhookPath=$webhookPath
            webhookDomain=$webhookDomain
            port=$port
            mongo.url=${mongo.url}
            mongo.user=${mongo.user?.length ?: 0} characters
            mongo.password=${mongo.password?.length ?: 0} characters
        """.trimIndent()
    }
}

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
            port = getFromSources("port").toInt(),
            mongo = MongoConfig(
                url = getFromSources("mongo_url"),
                user = getNullableFromSources("mongo_user"),
                password = getNullableFromSources("mongo_password"),
            )
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