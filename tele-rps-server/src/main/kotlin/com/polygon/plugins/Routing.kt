package com.polygon.plugins

import com.polygon.ConfigLoader
import com.polygon.InboundConnection
import com.polygon.SessionHandler
import com.polygon.tg.WebhookTelegramBot
import com.polygon.util.generateToken
import com.polygon.util.signedHash
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("nothing to see here")
        }

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
            if (call.request.headers["Authorization"] == signedHash) {
                val connection = InboundConnection(token = generateToken())
                SessionHandler.inboundConnections[connection.token] = connection
                call.respondText(connection.token)
            }
            call.respond(HttpStatusCode.Forbidden)
        }
    }
}
