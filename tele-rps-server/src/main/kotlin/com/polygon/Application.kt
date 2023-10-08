package com.polygon

import com.polygon.plugins.configureRouting
import com.polygon.plugins.configureSockets
import com.polygon.tg.PollingTelegramBot
import com.polygon.tg.WebhookTelegramBot
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    if (ConfigLoader.config.pollingMode) {
        PollingTelegramBot.startPolling()
    } else {
        WebhookTelegramBot.startWebHook()
    }
    embeddedServer(Netty, port = ConfigLoader.config.port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSockets()
    configureRouting()
}
