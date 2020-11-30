package com.get_your_music_5.users_system.services

import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.repositories.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(
        private val userRepository: UserRepository,
        private val encoder: PasswordEncoder

) {
    fun getAll(): List<User> = userRepository.findAll()

    fun getById(userId: Long): User? = userRepository.findById(userId).orElse(null)

    @Transactional
    fun save(user: User): User {
        user.password = encoder.encode(user.password)
        return userRepository.save(user)
    }

    fun existsByEmail(email: String): Boolean{
        return userRepository.existsByEmail(email)
    }

    @Transactional
    fun update(existed: User, user: User): User {
        existed.email = user.email
        return userRepository.save(existed)
    }

}