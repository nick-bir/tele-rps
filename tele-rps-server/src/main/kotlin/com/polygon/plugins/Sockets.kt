package com.polygon.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.polygon.GameController
import com.polygon.mongo.GameResult
import com.polygon.mongo.GamesRepository
import com.polygon.socket.*
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
            val authorized = uriComponents.size == 2 && InboundSessionHandler.authorizeToken(uriComponents[1])
            if (!authorized) {
                println("not authorized")
                close()
                return@webSocket
            }
            val session = SocketSession.openSession(this)
            for (frame in incoming) {
                println(frame.toString())
                if (frame is Frame.Text) {
                    val text = frame.readText()
                    val incomingMessage = objectMapper.readValue<InMessage>(text)
                    session.stateMachine(objectMapper, incomingMessage)
                }
            }
            SocketSession.closeSession(session)
        }
    }
}

suspend fun SocketSession.stateMachine(objectMapper: ObjectMapper, incomingMessage: InMessage) {
    if (incomingMessage.type == null) {
        return closeWithMessage("no type")
    }
    if (incomingMessage.from == null) {
        return closeWithMessage("no sender")
    }
    if (incomingMessage.type == IncomingMessageType.HELLO) {
        val game = GamesRepository.getLastByPlayerId(incomingMessage.from).await() ?: return closeWithMessage("no game")
        SocketSession.setGameInfo(this, game.playerId, game.gameId)
        return
    }
    if (incomingMessage.type == IncomingMessageType.MOVE) {
        val gameId = this.gameId ?: return closeWithMessage("no game")
        val gesture = incomingMessage.gesture ?: return closeWithMessage("no gesture")
        val game = GameController.makeMove(incomingMessage.from, gameId, gesture)
        if (game != null) {
            val sessions = SocketSession.sessionsByGameId(game.gameId)
            sessions.forEach {
                val thisIsOpponent = it.playerId == game.opponentId
                val playerGesture = if (thisIsOpponent) game.opponentGesture else game.playerGesture
                val opponentGesture = if (thisIsOpponent) game.playerGesture else game.opponentGesture
                val result = when {
                    thisIsOpponent && game.result == GameResult.VICTORY -> GameResult.DEFEAT
                    thisIsOpponent && game.result == GameResult.DEFEAT -> GameResult.VICTORY
                    else -> game.result
                }
                val message = OutMessage(game.status, playerGesture, opponentGesture, result)
                val strMessage = objectMapper.writeValueAsString(message)
                it.session.send(strMessage)
            }
        }
    }
}

suspend fun SocketSession.closeWithMessage(message: String) {
    session.close(CloseReason(4000, "no game"))
    println("$message. closing")
}