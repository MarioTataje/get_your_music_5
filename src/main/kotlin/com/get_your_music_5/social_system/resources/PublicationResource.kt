package com.get_your_music_5.social_system.resources

data class SavePublicationResource(
        val videoUrl: String,
        val content: String
)

data class PublicationResource(
        val id: Long?,
        val videoUrl: String,
        val content: String,
        val publishDate: String,
        val musicianName: String?
)