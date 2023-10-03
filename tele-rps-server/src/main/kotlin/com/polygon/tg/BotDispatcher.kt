package com.polygon.tg

import com.github.kotlintelegrambot.dispatcher.Dispatcher
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.dispatcher.text
import com.github.kotlintelegrambot.entities.ChatId
import com.polygon.text.TextResolver

val tgDispatcher: Dispatcher.() -> Unit = {
    println("dispatch")
    addHandler(LoggingHandler)
    command("start") {
        println("start")
        bot.sendMessage(
            ChatId.fromId(message.chat.id),
            text = TextResolver.fromCode(message.from?.languageCode).start,
        )
        update.consume()
    }
    text {
        bot.sendMessage(ChatId.fromId(message.chat.id), text = TextResolver.fromCode(message.from?.languageCode).unknown)
        update.consume()
    }
}