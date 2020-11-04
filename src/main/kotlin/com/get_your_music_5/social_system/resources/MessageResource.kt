package com.get_your_music_5.social_system.resources

data class SaveMessageResource(
        val text: String
)

data class MessageResource(
        val id: Long?,
        val text: String,
        val sendDate: String,
        val senderName: String?,
        val receiverName: String?
)