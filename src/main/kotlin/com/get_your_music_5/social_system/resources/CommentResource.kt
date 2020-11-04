package com.get_your_music_5.social_system.resources

data class SaveCommentResource(
        val text: String
)

data class CommentResource(
        val id: Long?,
        val text: String,
        val commenterName: String?,
        val publicationContent: String?
)