package com.polygon

import com.polygon.mongo.*
import kotlinx.coroutines.Deferred

enum class ChallengeResult {
    NOT_FOUND,
    SAME_PLAYER,
    ANOTHER_OPPONENT,
    GAME_COMPLETED,
    OK
}

object GameController {
    fun createGame(challenger: Long): Deferred<Game> {
        return GamesRepository.insert(Game.createPending(challenger))
    }

    suspend fun processChallenge(userId: Long, gameId: String): Pair<ChallengeResult, Game?> {
        var game = GamesRepository.get(gameId).await()
        val res = when {
            game == null -> ChallengeResult.NOT_FOUND
            game.playerId == userId -> ChallengeResult.SAME_PLAYER
            game.opponentId != null -> ChallengeResult.ANOTHER_OPPONENT
            game.status == GameStatus.COMPLETED -> ChallengeResult.GAME_COMPLETED
            else -> {
                game = game.copy(status = GameStatus.IN_PROGRESS, opponentId = userId)
                GamesRepository.update(game).await()
                ChallengeResult.OK
            }
        }
        return res to game
    }

    suspend fun makeMove(userId: Long, gameId: String, move: Gesture): Game? {
        val game = GamesRepository.get(gameId).await() ?: return null
        val playerGesture: Gesture?
        val opponentGesture: Gesture?
        if (game.playerId == userId) {
            playerGesture = move
            opponentGesture = game.opponentGesture
        } else if (game.opponentId == userId) {
            playerGesture = game.playerGesture
            opponentGesture = move
        } else {
            return null
        }
        val gameResult = gameResult(playerGesture, opponentGesture)
        val gameStatus = if (gameResult != null) GameStatus.COMPLETED else GameStatus.IN_PROGRESS
        val newGame = game.copy(result = gameResult, playerGesture = playerGesture, opponentGesture = opponentGesture, status = gameStatus)
        GamesRepository.update(newGame).await()
        return newGame
    }

    fun gameResult(gestureA: Gesture?, gestureB: Gesture?) = when {
        gestureA == null || gestureB == null -> null
        gestureA == gestureB -> GameResult.DRAW
        gestureA.ordinal == gestureB.ordinal + 1 -> GameResult.VICTORY
        gestureA.ordinal + 2 == gestureB.ordinal -> GameResult.VICTORY
        else -> GameResult.DEFEAT
    }
}