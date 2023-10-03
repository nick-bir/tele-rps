package com.polygon.mongo

import com.mongodb.kotlin.client.coroutine.MongoClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

object MongoDriver {
    private val mongoUri = "mongodb://root:example@127.0.0.1:27017"
    private val client = MongoClient.create(mongoUri)
    private val mongoScope = CoroutineScope(Dispatchers.Default)
    private val db = client.getDatabase("rps")
    private val gamesTable = db.getCollection<Game>("games")

    fun insertGame(game: Game): Deferred<Game> {
        return mongoScope.async {
            gamesTable.insertOne(game)
            return@async game
        }
    }

    fun listGames(): Deferred<List<Game>> {
        return mongoScope.async {
            val games = mutableListOf<Game>()
            gamesTable.find().onEach {
                games.add(it)
            }.collect()
            return@async games
        }
    }
}