package com.get_your_music_5.auth.resources

data class MessageResponse(val message: String)

data class LoginResource(val email: String, val password: String)

data class JwtResponse(val token: String)