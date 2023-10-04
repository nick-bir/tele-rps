package com.polygon.text

object EngText : TextResolver() {
    override val start: String
        get() = "Hi! Delighted to see you here. Try out our brand new game. It's in the menu button ;)"
    override val startChallenge: String
        get() = """
            Here's how to play:
            1. Send me /challenge command
            2. I'll send you the button for your friend
            3. They need to click the button and I'll match you
            4. Open the mini-app in menu and play""".trimIndent()

    override val play: String
        get() = "Play!"

    override val inviteChallenge: String
        get() = "Play the ultimate ROCK-PAPER-SCISSORS with me! Hit the button below!"

    override val challengeAccepted: String
        get() = "Challenge accepted! Let the battle begin! Open the game in the menu button".trimIndent()
    override val unknown: String
        get() = "Sorry, not sure what you mean. Again, all I can is play games"

    override val noUser: String
        get() = "Sorry, I can't play with you if I don't know who you are :("

    override val samePlayer: String
        get() = "Looks like you clicked the button yourself. It's ok, but you still need to forward it to your friend"

    override val gameNotFound: String
        get() = "Hmm... Something is wrong. Try to challenge your friend again"

    override val gameInProgress: String
        get() = "This game is already in progress. open the app and play!"
    override val gameCompleted: String
        get() = "This game is already completed. If you want to play a new one just send me /challenge again and I'll do the rest"
}
