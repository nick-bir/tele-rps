package com.polygon.text

sealed class TextResolver {
    abstract val start: String
    abstract val startChallenge: String
    abstract val play: String
    abstract val inviteChallenge: String
    abstract val challengeAccepted: String
    abstract val samePlayer: String
    abstract val gameNotFound: String
    abstract val gameInProgress: String
    abstract val gameCompleted: String
    abstract val unknown: String
    abstract val noUser: String

    companion object {
        fun fromCode(languageCode: String?): TextResolver = when (languageCode) {
            "ru" -> RuText
            else -> EngText
        }
    }
}
