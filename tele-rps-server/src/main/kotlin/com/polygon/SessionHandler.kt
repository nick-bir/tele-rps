package com.polygon

import io.ktor.websocket.*
import org.joda.time.DateTime
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class SocketConnection(val session: DefaultWebSocketSession) {
    var playerId: Long? = null
    var gameId: Long? = null
}

data class InboundConnection (val token: String, val expires: DateTime) {
    constructor(token: String) : this(token, expires = DateTime().plusSeconds(ConfigLoader.config.socketTokenTimeoutSec))
}

object SessionHandler {
    val socketConnections: MutableSet<SocketConnection> = Collections.synchronizedSet<SocketConnection>(HashSet())
    val inboundConnections: MutableMap<String, InboundConnection> = Collections.synchronizedMap<String, InboundConnection>(HashMap())

    fun authorizeToken(token: String): Boolean {
        val connection = inboundConnections.remove(token)
        return connection != null && connection.expires.isAfterNow
    }
}