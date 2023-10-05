package com.polygon.socket

import com.polygon.mongo.GameResult
import com.polygon.mongo.Gesture

data class Message(val from: Long?, val move: Gesture?, val gameResult: GameResult?)