package com.polygon.mongo

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

data class Game(
    val gameId: String,
    val playerId: String,
    val playerGesture: Gesture,
    val opponentId: String,
    val opponentGesture: Gesture,
    val result: GameResult
)