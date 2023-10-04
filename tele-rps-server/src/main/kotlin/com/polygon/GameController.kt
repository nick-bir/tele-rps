package com.polygon

import com.polygon.mongo.Game
import com.polygon.mongo.GameStatus
import com.polygon.mongo.GamesRepository
import kotlinx.coroutines.Deferred

enum class ChallengeResult {
    NOT_FOUND,
    SAME_PLAYER,
    GAME_IN_PROGRESS,
    GAME_COMPLETED,
    OK
}

object GameController {
    fun createGame(challenger: Long): Deferred<Game> {
        return GamesRepository.insert(Game.createPending(challenger))
    }

    suspend fun processChallenge(userId: Long, gameId: String): Pair<ChallengeResult, Game?> {
        val game = GamesRepository.get(gameId).await()
        val res = when {
            game == null -> ChallengeResult.NOT_FOUND
            game.playerId == userId -> ChallengeResult.SAME_PLAYER
            game.status == GameStatus.IN_PROGRESS -> ChallengeResult.GAME_IN_PROGRESS
            game.status == GameStatus.COMPLETED -> ChallengeResult.GAME_COMPLETED
            else -> ChallengeResult.OK
        }
        return res to game
    }
}