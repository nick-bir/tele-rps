package com.polygon.socket

import com.polygon.mongo.GameResult
import com.polygon.mongo.GameStatus
import com.polygon.mongo.Gesture

enum class IncomingMessageType {
    HELLO,
    MOVE
}
data class InMessage(val type: IncomingMessageType?, val from: Long?, val gesture: Gesture?)
data class OutMessage(val gameStatus: GameStatus, val yourGesture: Gesture?, val opponentGesture: Gesture?, val gameResult: GameResult?)