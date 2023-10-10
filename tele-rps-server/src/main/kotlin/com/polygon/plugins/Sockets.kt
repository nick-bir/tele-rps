package com.polygon.plugins

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.polygon.GameController
import com.polygon.mongo.Game
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
        return closeWithMessage("NO_TYPE")
    }
    if (incomingMessage.from == null) {
        return closeWithMessage("NO_SENDER")
    }
    if (incomingMessage.type == IncomingMessageType.HELLO) {
        val game = GamesRepository.getLastByPlayerId(incomingMessage.from).await() ?: return closeWithMessage("NO_GAME")
        SocketSession.setGameInfo(this, incomingMessage.from, game.gameId)
        sendGameUpdate(objectMapper, game)
        return
    }
    if (incomingMessage.type == IncomingMessageType.MOVE) {
        val gameId = this.gameId ?: return closeWithMessage("NO_GAME")
        val gesture = incomingMessage.gesture ?: return closeWithMessage("NO_GESTURE")
        val game = GameController.makeMove(incomingMessage.from, gameId, gesture)
        if (game != null) {
            val sessions = SocketSession.sessionsByGameId(game.gameId)
            sessions.forEach {
                it.sendGameUpdate(objectMapper, game)
            }
        }
    }
}

suspend fun SocketSession.closeWithMessage(message: String) {
    session.close(CloseReason(4000, message))
    println("$message. closing")
}

suspend fun SocketSession.sendGameUpdate(objectMapper: ObjectMapper, game: Game) {
    val thisIsOpponent = this.playerId == game.opponentId
    val playerGesture = if (thisIsOpponent) game.opponentGesture else game.playerGesture
    val opponentGesture = if (thisIsOpponent) game.playerGesture else game.opponentGesture
    val result = when {
        thisIsOpponent && game.result == GameResult.VICTORY -> GameResult.DEFEAT
        thisIsOpponent && game.result == GameResult.DEFEAT -> GameResult.VICTORY
        else -> game.result
    }
    val message = OutMessage(game.status, playerGesture, opponentGesture, result)
    val strMessage = objectMapper.writeValueAsString(message)
    session.send(strMessage)
}