package com.polygon.text

object EngText: TextResolver() {
    override val start: String
        get() = "Hi! Delighted to see you here. Try out our brand new game. It's in the menu button ;)"
    override val unknown: String
        get() = "Sorry, not sure what you mean. Again, all I can is play games"
}
