package com.polygon.text

object RuText: TextResolver() {
    override val start: String
        get() = "Дарова! Ну шо, как насчёт в шамхматишки? Или ещё чего? Кнопка в меню"
    override val unknown: String
        get() = "Дружище, ты о чём? Я думал мы серьёзными делами займёмся. Просто нажми кнопку и поиграем"
}
