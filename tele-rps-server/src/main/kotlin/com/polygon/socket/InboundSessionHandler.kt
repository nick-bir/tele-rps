package com.polygon.socket

import com.polygon.ConfigLoader
import com.polygon.util.generateToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.joda.time.DateTime
import kotlin.time.Duration.Companion.hours

/**
 * This object stores one-time tokens for WS connections
 * UI sends request to open socket with ?$token parameter
 */
object InboundSessionHandler {
    private data class InboundConnection(val token: String, val expires: DateTime) {
        constructor(token: String) : this(
            token,
            expires = DateTime().plusSeconds(ConfigLoader.config.socketTokenTimeoutSec)
        )
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

    fun runCleanUpWorker() {
        val ctx = CoroutineScope(Dispatchers.Default)
        ctx.launch {
            while (true) {
                delay(1.hours)
                mutex.withLock {
                    val expiredKeys = inboundConnections.filterValues { it.expires.isBeforeNow }.keys
                    expiredKeys.forEach {
                        inboundConnections.remove(it)
                    }
                    println("Removed ${expiredKeys.size} expired connections")
                }
            }
        }
    }
}