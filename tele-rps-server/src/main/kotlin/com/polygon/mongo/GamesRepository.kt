package com.polygon.mongo

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.or
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

object GamesRepository {
    private val mongoUri = "mongodb://root:example@127.0.0.1:27017"
    private val client = MongoClient.create(mongoUri)
    private val mongoScope = CoroutineScope(Dispatchers.IO)
    private val db = client.getDatabase("rps")
    private val gamesTable = db.getCollection<Game>("games")

    fun insert(game: Game): Deferred<Game> {
        return mongoScope.async {
            gamesTable.insertOne(game)
            return@async game
        }
    }

    fun get(id: String): Deferred<Game?> {
        return mongoScope.async {
            return@async gamesTable.find(eq("gameId", id)).firstOrNull()
        }
    }

    fun update(game: Game): Deferred<UpdateResult> {
        return mongoScope.async {
            return@async gamesTable.replaceOne(eq("gameId", game.gameId), game)
        }
    }

    fun getLastByPlayerId(playerId: Long): Deferred<Game?> {
        return mongoScope.async {
            val games = gamesTable.find(or(eq("playerId", playerId), eq("opponentId", playerId)))
            if (games.count() == 0) {
                return@async null
            }
            return@async games.reduce { g1, g2 ->
                if (g1.lastUpdate > g2.lastUpdate) {
                    g1
                } else {
                    g2
                }
            }
        }
    }
}