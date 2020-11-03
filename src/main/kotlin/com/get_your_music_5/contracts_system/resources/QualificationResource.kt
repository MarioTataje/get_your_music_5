package com.get_your_music_5.contracts_system.resources

data class SaveQualificationResource(
        val text: String,
        val score: Int
)

data class QualificationResource(
        val id: Long?,
        val text: String,
        val score: Int,
        val organizerName: String?
)