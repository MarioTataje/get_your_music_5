package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.resources.SaveUserResource
import com.get_your_music_5.users_system.resources.UserResource
import com.get_your_music_5.users_system.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(
        private val userService: UserService
) {
    @GetMapping("/users/")
    fun getAllUsers(): ResponseEntity<List<UserResource>> {
        return try{
            val users = userService.getAll()
            if (users.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(users.map { user -> this.toResource(user)  }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/users/{id}/")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserResource> {
        val existed = userService.getById(id)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    @PutMapping("/users/{id}/")
    fun update(@PathVariable id: Long, @RequestBody user: SaveUserResource): ResponseEntity<UserResource> {
        val existed = userService.update(id, toEntity(user))
        return ResponseEntity(toResource(existed), HttpStatus.OK)
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