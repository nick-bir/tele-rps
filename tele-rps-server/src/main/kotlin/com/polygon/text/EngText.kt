package com.polygon.text

object EngText : TextResolver() {
    override val start: String
        get() = """
            Rock-paper-scissors!
            
            ABOUT THE GAME
            
            Challenge anyone in the world to conclude an argument, toss a coin or simply for fun. Remember, rock breaks scissors, paper wraps the rock and scissors cut paper. Good luck!

            HOW TO PLAY
            
            1. Send /challenge in chat 
            2. Forward button PLAY to your opponent to challenge them
            3. Open the app in the menu and battle!
        """.trimIndent()

    override val play: String
        get() = "PLAY"

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

    override val gameWithAnotherOpponent: String
        get() = "Your opponent is already playing with someone else. You can /challenge them again!"
    override val gameCompleted: String
        get() = start
}
