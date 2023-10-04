package com.polygon.mongo

import com.mongodb.client.model.Filters.eq
import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onEach

object GamesRepository {
    private val mongoUri = "mongodb://root:example@127.0.0.1:27017"
    private val client = MongoClient.create(mongoUri)
    private val mongoScope = CoroutineScope(Dispatchers.Default)
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

    fun list(): Deferred<List<Game>> {
        return mongoScope.async {
            val games = mutableListOf<Game>()
            gamesTable.find().onEach {
                games.add(it)
            }.collect()
            return@async games
        }
    }
}