package com.get_your_music_5.users_system.repositories

import com.get_your_music_5.users_system.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long>