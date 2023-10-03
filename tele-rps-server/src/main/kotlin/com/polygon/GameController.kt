package com.polygon

import com.polygon.mongo.Game
import com.polygon.mongo.MongoDriver
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

object GameController {
    fun createGame(challenger: Long): Deferred<Game> {
        return MongoDriver.insertGame(Game.createPending(challenger))
    }

    fun processChallenge(userId: Long, gameId: String): Job {
        TODO()
    }
}