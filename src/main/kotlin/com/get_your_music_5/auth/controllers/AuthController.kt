package com.get_your_music_5.auth.controllers

import com.get_your_music_5.auth.JwtUtil
import com.get_your_music_5.auth.resources.JwtResponse
import com.get_your_music_5.auth.resources.LoginResource
import com.get_your_music_5.auth.resources.MessageResponse
import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.resources.SaveUserResource
import com.get_your_music_5.users_system.resources.UserResource
import com.get_your_music_5.users_system.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class AuthController(
        private val manager: AuthenticationManager,
        private val jwtUtil: JwtUtil,
        private val userService: UserService
)  {

    @PostMapping("/register/")
    fun register(@RequestBody user: SaveUserResource): ResponseEntity<*>? {
        return if(userService.existsByEmail(user.email)) {
            ResponseEntity.badRequest().body<Any>(MessageResponse("Email is already in use"))
        }
        else try{
            val userResource = userService.save(toEntity(user))
            ResponseEntity<Any>(toResource(userResource), HttpStatus.CREATED)
        } catch (e: Exception){
            ResponseEntity<Any>(null, HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/login/")
    fun login(@RequestBody user: LoginResource): ResponseEntity<*>?{
        val authentication = manager.authenticate(
                UsernamePasswordAuthenticationToken(user.email, user.password))
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = jwtUtil.generateJwtToken(authentication)
        return ResponseEntity.ok<Any>(JwtResponse(jwt))
    }

    fun toEntity(resource: SaveUserResource) = User(
            email = resource.email,
            password = resource.password
    )

    fun toResource(entity: User) = UserResource(
            id = entity.id,
            email = entity.email
    )
}