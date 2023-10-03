package com.polygon.text

sealed class TextResolver {
    abstract val start: String
    abstract val unknown: String

    companion object {
        fun fromCode(languageCode: String?): TextResolver = when (languageCode) {
            "ru" -> RuText
            else -> EngText
        }
    }
}
