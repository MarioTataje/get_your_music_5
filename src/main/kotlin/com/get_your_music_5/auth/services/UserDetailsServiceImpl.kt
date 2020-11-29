package com.get_your_music_5.auth.services

import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.repositories.UserRepository
import com.get_your_music_5.auth.models.UserDetailsImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
class UserDetailsServiceImpl (private val userRepository: UserRepository) : UserDetailsService {

    fun findEmailById(idString: String): UserDetails? {
        val id = idString.toLong()
        val user: User = userRepository.findById(id).orElse(null) ?: return null
        return UserDetailsImpl.build(user)
    }

    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: User = userRepository.findByEmail(email)
        return UserDetailsImpl.build(user)
    }

}
