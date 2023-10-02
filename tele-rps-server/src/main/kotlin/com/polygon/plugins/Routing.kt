package com.polygon.plugins

import com.polygon.InboundConnection
import com.polygon.SessionHandler
import com.polygon.util.generateToken
import com.polygon.util.signedHash
import io.ktor.http.*
import io.ktor.server.application.*
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
