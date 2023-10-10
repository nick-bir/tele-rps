package com.polygon.plugins

import com.polygon.ConfigLoader
import com.polygon.socket.InboundSessionHandler
import com.polygon.tg.WebhookTelegramBot
import com.polygon.util.validateTgInitData
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        options("*") {
            call.response.headers.append("Access-Control-Allow-Origin", "*")
            call.response.headers.append("Access-Control-Allow-Headers", "*")
            call.respond(HttpStatusCode.OK, "")
        }

        if (!ConfigLoader.config.pollingMode) {
            val hookPath = ConfigLoader.config.webhookPath ?: throw Exception("Webhook URL is not configured")
            post("/$hookPath") {
                val response = call.receiveText()
                WebhookTelegramBot.processUpdate(response)
                call.respond(HttpStatusCode.OK)
            }
        }

        post("/auth") {
            call.response.headers.append("Access-Control-Allow-Origin", "*")
            call.response.headers.append("Access-Control-Allow-Headers", "*")
            val body = call.receiveText()
            if (validateTgInitData(body)) {
                val token = InboundSessionHandler.issueToken()
                call.respondText(token)
            }
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}
