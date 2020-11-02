package com.get_your_music_5.users_system.resources

data class SaveUserResource (
        val email: String,
        val password: String)

data class UserResource (
        val id: Long?,
        val email: String)