package com.get_your_music_5.users_system.services

import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class UserService(
        private val userRepository: UserRepository
) {
    fun getAll(): List<User> = userRepository.findAll()

    fun getById(userId: Long): User {
        return userRepository.findById(userId)
                .orElseThrow { throw IllegalArgumentException("User not found $userId") }
    }

    @Transactional
    fun save(user: User): User {
        return userRepository.save(user)
    }

    @Transactional
    fun update(userId: Long, user: User): User {
        val existed = userRepository.findById(userId).
        orElseThrow { throw IllegalArgumentException("User not found $userId") }
        existed.email = user.email
        return userRepository.save(existed)
    }

}