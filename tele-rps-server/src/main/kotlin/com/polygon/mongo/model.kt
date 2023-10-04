package com.polygon.mongo

import java.util.Date
import java.util.UUID

enum class GameResult {
    VICTORY,
    DEFEAT,
    DRAW;
}

enum class Gesture {
    ROCK,
    PAPER,
    SCISSORS;
}

enum class GameStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED;
}

data class Game(
    val gameId: String,
    val status: GameStatus,
    val lastUpdate: Date,
    val playerId: Long,
    val playerGesture: Gesture?,
    val opponentId: Long?,
    val opponentGesture: Gesture?,
    val result: GameResult?
) {
    companion object {
        fun createPending(userId: Long): Game {
            val uuid = UUID.randomUUID().toString()
            return Game(
                gameId = uuid,
                status = GameStatus.PENDING,
                lastUpdate = Date(),
                playerId = userId,
                playerGesture = null,
                opponentId = null,
                opponentGesture = null,
                result = null
            )
        }
    }
}