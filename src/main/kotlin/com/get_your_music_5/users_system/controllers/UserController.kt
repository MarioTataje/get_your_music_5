package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.resources.SaveUserResource
import com.get_your_music_5.users_system.resources.UserResource
import com.get_your_music_5.users_system.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(
        private val userService: UserService
) {
    @GetMapping("/users")
    fun getAllUsers(): List<UserResource> {
        val users = userService.getAll()
        return toResourceList(users)
    }

    @GetMapping("/users/{userId}")
    fun getUserById(@PathVariable userId: Long): UserResource {
        val existed = userService.getById(userId)
        return toResource(existed)
    }

    @PostMapping("/users")
    fun create(@RequestBody user: SaveUserResource) : UserResource {
        val newUser = userService.save(toEntity(user))
        return toResource(newUser)
    }

    @PutMapping("/users/{userId}")
    fun update(@PathVariable userId: Long, @RequestBody user: SaveUserResource): UserResource {
        val existed = userService.update(userId, toEntity(user))
        return toResource(existed)
    }

    fun toEntity(resource: SaveUserResource) = User(
            email = resource.email,
            password = resource.password
    )

    fun toResourceList(entities: List<User>) : List<UserResource>{
        val resources = mutableListOf<UserResource>()
        for(entity in entities){
            val resource = UserResource(entity.id, entity.email)
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: User) = UserResource(
            id = entity.id,
            email = entity.email
    )
}