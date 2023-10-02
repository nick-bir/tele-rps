package com.polygon.plugins

import com.polygon.SessionHandler
import com.polygon.SocketConnection
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.time.Duration

fun Application.configureSockets() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
    routing {
        webSocket("/ws") {
            println("open socket")
            val uriComponents = call.request.uri.split('?')
            val authorized = uriComponents.size == 2 && SessionHandler.authorizeToken(uriComponents[1])
            if (!authorized) {
                println("not authorized")
                close()
            } else {
                SessionHandler.socketConnections.add(SocketConnection(session = this))
            }
            for (frame in incoming) {
                println(frame.toString())
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    outgoing.send(Frame.Text("YOU SAID: $text"))
                }
            }
            println("closed socket")
        }
    }
}
