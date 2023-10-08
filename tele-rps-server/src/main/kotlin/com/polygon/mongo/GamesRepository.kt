package com.polygon.mongo

import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Filters.or
import com.mongodb.client.result.UpdateResult
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.polygon.ConfigLoader
import com.polygon.MongoConfig
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import org.slf4j.LoggerFactory
import java.util.*

object GamesRepository {
    private fun buildMongoUri(cfg: MongoConfig): String {
        val userPart = if (cfg.user != null) "${cfg.user}:${cfg.password}@" else ""
        return "mongodb://$userPart@${cfg.url}"
    }

    private val mongoUri = buildMongoUri(ConfigLoader.config.mongo)
    private val client = MongoClient.create(mongoUri)
    private val mongoScope = CoroutineScope(Dispatchers.IO)
    private val db = client.getDatabase("rps")
    private val gamesTable = db.getCollection<Game>("games")
    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    fun insert(game: Game): Deferred<Game> {
        return mongoScope.async {
            LOGGER.trace("INSERT: {}", game)
            gamesTable.insertOne(game)
            return@async game
        }
    }

    fun get(id: String): Deferred<Game?> {
        return mongoScope.async {
            LOGGER.trace("GET: {}", id)
            return@async gamesTable.find(eq("_id", id)).firstOrNull()
        }
    }

    fun update(game: Game): Deferred<UpdateResult> {
        return mongoScope.async {
            LOGGER.trace("REPLACE: {}", game)
            return@async gamesTable.replaceOne(eq("_id", game.gameId), game.copy(lastUpdate = Date()))
        }
    }

    fun getLastByPlayerId(playerId: Long): Deferred<Game?> {
        return mongoScope.async {
            LOGGER.trace("GET BY: {}", playerId)
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