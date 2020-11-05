package com.get_your_music_5.social_system.services

import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.repositories.FollowingRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class FollowingService(
        private val followingRepository: FollowingRepository,
        private val musicianRepository: MusicianRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)
    fun getAllByFollowerId(followerId: Long): List<Following> = followingRepository.getByFollowerId(followerId)
    fun getAllByFollowedId(followedId: Long): List<Following> = followingRepository.getByFollowedId(followedId)
    fun getById(followerId: Long, followedId: Long): Following =
            followingRepository.findByFollowerIdAndFollowedId(followerId, followedId)
    fun save(followerId: Long, followedId: Long): Following? {
        val exist = followingRepository.findByFollowerIdAndFollowedId(followerId, followedId)
        if (exist != null) {
            return null
        }
        val newFollowing = Following()
        newFollowing.follower = musicianRepository.findById(followerId).orElse(null)
        newFollowing.followed = musicianRepository.findById(followedId).orElse(null)
        newFollowing.followDate = currentDateFormat
        return followingRepository.save(newFollowing)
    }
    fun delete(followerId: Long, followedId: Long){
        val exist = followingRepository.findByFollowerIdAndFollowedId(followerId, followedId)
        followingRepository.delete(exist)
    }
}