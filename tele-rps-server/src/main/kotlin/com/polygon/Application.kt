package com.polygon

import com.polygon.plugins.*
import com.polygon.tg.polling.PollingTelegramBot
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    PollingTelegramBot.startPolling()
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSockets()
    configureRouting()
}
