package com.polygon.text

object RuText: TextResolver() {
    override val start: String
        get() = "Дарова! Ну шо, как насчёт в шамхматишки? Или ещё чего? Кнопка в меню"
    override val startChallenge: String
        get() = """
            Here's how to play:
            1. Send me /challenge command
            2. I'll send you the button for your friend
            3. They need to click the button and I'll match you
            4. Open the mini-app in menu and play""".trimIndent()

    override val inviteChallenge: String
        get() = "Play the ultimate ROCK-PAPER-SCISSORS with me! Hit the button below!"

    override val challengeAccepted: String
        get() = "Challenge accepted! Let the battle begin! Open the game in the menu button".trimIndent()

    override val noUser: String
        get() = "Sorry, I can't play with you if I don't know who you are :("

    override val unknown: String
        get() = "Дружище, ты о чём? Я думал мы серьёзными делами займёмся. Просто нажми кнопку и поиграем"

    override val play: String
        get() = "Играть!"

}
