package com.polygon.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.polygon.SessionHandler
import com.polygon.SocketConnection
import com.polygon.socket.Message
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
        val objectMapper = ObjectMapper().registerKotlinModule()

        webSocket("/ws") {
            println("open socket")
            val uriComponents = call.request.uri.split('?')
            val authorized = uriComponents.size == 2 && SessionHandler.authorizeToken(uriComponents[1])
            if (!authorized) {
                println("not authorized")
                close()
                return@webSocket
            }
            val socketConnection = SocketConnection(session = this)
            SessionHandler.socketConnections.add(socketConnection)
            for (frame in incoming) {
                println(frame.toString())
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val message = objectMapper.readValue<Message>(text)
                    stateMachine(objectMapper, socketConnection, message)
                }
            }
            SessionHandler.socketConnections.remove(socketConnection)
            println("closed socket")
        }
    }
}

suspend fun DefaultWebSocketServerSession.stateMachine(objectMapper: ObjectMapper, socket: SocketConnection, message: Message) {
    if (socket.playerId == null) {
        if (message.from == null) {
            close(CloseReason(4000, "no sender"))
            println("no player id. closing")
            return
        }
        socket.playerId = message.from
        val text = objectMapper.writeValueAsString(message)
        outgoing.send(Frame.Text(text))
    }
}
