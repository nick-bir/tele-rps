package com.polygon.socket

import io.ktor.websocket.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SocketSession(val id: Long, val session: DefaultWebSocketSession) {
    var playerId: Long? = null
        private set
    var gameId: String? = null
        private set

    companion object SessionHandler {
        private val sessions: MutableMap<Long, SocketSession> = mutableMapOf()
        private val sessionsByGameId: MutableMap<String, MutableList<SocketSession>> = mutableMapOf()
        private val connectionMutex = Mutex()
        private var connectionCounter = 0L

        suspend fun openSession(wsSession: DefaultWebSocketSession): SocketSession {
            connectionMutex.withLock {
                val sessionId = connectionCounter++
                val session = SocketSession(sessionId, wsSession)
                sessions[sessionId] = session
                return session
            }
        }

        suspend fun sessionsByGameId(gameId: String): List<SocketSession> {
            connectionMutex.withLock {
                return sessionsByGameId[gameId] ?: emptyList()
            }
        }

        suspend fun setGameInfo(session: SocketSession, playerId: Long, gameId: String) {
            connectionMutex.withLock {
                session.playerId = playerId
                session.gameId = gameId
                val sameGameSessions = sessionsByGameId.getOrPut(gameId) { mutableListOf() }
                sameGameSessions += session
            }
        }

        suspend fun closeSession(session: SocketSession) {
            connectionMutex.withLock {
                sessions.remove(session.id)
                if (session.gameId != null) {
                    val gameSessions = sessionsByGameId[session.gameId]
                    if (gameSessions != null) {
                        gameSessions.remove(session)
                        if (gameSessions.isEmpty()) {
                            sessionsByGameId.remove(session.gameId)
                        }
                    }
                }
            }
        }
    }
}

