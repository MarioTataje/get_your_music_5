package com.get_your_music_5.users_system.resources

data class SaveProfileResource (
        val firstName: String,
        val lastName: String,
        val birthDate: String,
        val phone: String,
        val description: String,
        val photoUrl: String,
        val type: String,
        val districtId: Long
)

open class ProfileResource(
        val id: Long?,
        val firstName: String,
        val lastName: String,
        val birthDate: String,
        val phone: String,
        val description: String,
        val registerDate: String,
        val photoUrl: String,
        val type: String,
        val userEmail: String?,
        val districtName: String?
)

