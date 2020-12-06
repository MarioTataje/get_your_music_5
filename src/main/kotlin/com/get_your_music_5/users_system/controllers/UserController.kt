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

    @GetMapping("/users/{userId}/")
    fun getUserById(@PathVariable userId: Long): ResponseEntity<UserResource> {
        return try{
            val existed = userService.getById(userId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/users/{userId}/")
    fun update(@PathVariable userId: Long, @RequestBody user: SaveUserResource): ResponseEntity<UserResource> {
        return try{
            val existed = userService.getById(userId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val updated = userService.update(existed, toEntity(user))
            ResponseEntity(toResource(updated), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
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