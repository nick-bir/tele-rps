package com.polygon.text

object RuText: TextResolver() {
    override val start: String
        get() = """
            Камень-ножницы-бумага!
            
            ОБ ИГРЕ
            
            Брось вызов игрокам со всего мира, чтобы решить спор, заменить жеребьевку или просто повеселиться. Не забывай, что камень ломает ножницы, бумага обертывает камень, а ножницы режут бумагу. Удачи!

            КАК ИГРАТЬ
            
            1. Отправь в чат /challenge 
            2. Перешли кнопку ИГРАТЬ своему противнику, чтобы бросить вызов
            3. Открой приложение в меню и сражайся!
        """.trimIndent()

    override val inviteChallenge: String
        get() = """
            Камень-ножницы-бумага!
            Кто из нас с тобой круче?
        """.trimIndent()

    override val challengeAccepted: String
        get() = "Вызов принят! Открой приложение в меню, выбери действие и ожидай ход противника.".trimIndent()

    override val noUser: String
        get() = "Прости, я не могу играть с незнакомцами \uD83D\uDE41 Проверь свои настройки конфиденциальности."

    override val unknown: String
        get() = "Прости, я тебя не понимаю. Зря я прогуливал пары в Академии искуственного интеллекта \uD83D\uDE41"

    override val play: String
        get() = "ИГРАТЬ"

    override val samePlayer: String
        get() = "Игра для двоих. Перешли кнопку ИГРАТЬ своему противнику."

    override val gameNotFound: String
        get() = "Хмм.. Что-то пошло не так. Попробуй бросить вызов снова."

    override val gameWithAnotherOpponent: String
        get() = "Похоже тебя не дождались и поиграли без тебя :("
    override val gameCompleted: String
        get() = start
}
