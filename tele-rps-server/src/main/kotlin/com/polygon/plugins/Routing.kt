package com.polygon.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        get("/") {
            call.respondText("Hello World!")
        }

        options("*") {
            call.response.headers.append("Access-Control-Allow-Origin", "*")
            call.response.headers.append("Access-Control-Allow-Headers", "*")
            call.respond(HttpStatusCode.OK, "")
        }

        post("/auth") {
            call.response.headers.append("Access-Control-Allow-Origin", "*")
            call.response.headers.append("Access-Control-Allow-Headers", "*")
            call.respondText("Hello World!")
        }
    }
}
