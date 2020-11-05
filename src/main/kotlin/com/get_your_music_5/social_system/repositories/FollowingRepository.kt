package com.get_your_music_5.social_system.repositories

import com.get_your_music_5.social_system.models.Following
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowingRepository: JpaRepository<Following, Long> {
    fun getByFollowerId(followerId: Long): List<Following>
    fun getByFollowedId(followedId: Long): List<Following>
    fun findByFollowerIdAndFollowedId(followerId: Long, followedId: Long): Following
}