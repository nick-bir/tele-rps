package com.polygon.socket

import com.polygon.ConfigLoader
import com.polygon.util.generateToken
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joda.time.DateTime

object InboundSessionHandler {
    private data class InboundConnection (val token: String, val expires: DateTime) {
        constructor(token: String) : this(token, expires = DateTime().plusSeconds(ConfigLoader.config.socketTokenTimeoutSec))
    }

    private val inboundConnections: MutableMap<String, InboundConnection> = mutableMapOf()
    private val mutex = Mutex()
    suspend fun authorizeToken(token: String): Boolean {
        mutex.withLock {
            val connection = inboundConnections.remove(token)
            return connection != null && connection.expires.isAfterNow
        }
    }

    suspend fun issueToken(): String {
        mutex.withLock {
            val connection = InboundConnection(token = generateToken())
            inboundConnections[connection.token] = connection
            return connection.token
        }
    }
}