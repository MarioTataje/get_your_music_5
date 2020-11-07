package com.get_your_music_5.social_system.services

import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.repositories.FollowingRepository
import com.get_your_music_5.users_system.patterns.ObserverImpl
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class FollowingService(
        private val followingRepository: FollowingRepository,
        private val musicianRepository: MusicianRepository,
        private val notificationRepository: NotificationRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)
    fun getAllByFollowerId(followerId: Long): List<Following> = followingRepository.getByFollowerId(followerId)
    fun getAllByFollowedId(followedId: Long): List<Following> = followingRepository.getByFollowedId(followedId)
    fun getById(followerId: Long, followedId: Long): Following =
            followingRepository.findByFollowerIdAndFollowedId(followerId, followedId)
    fun save(followerId: Long, followedId: Long): Following? {
        val following = Following()
        following.follower = musicianRepository.findById(followerId).orElse(null)
        following.followed = musicianRepository.findById(followedId).orElse(null)
        following.followDate = currentDateFormat
        following.addObserver(ObserverImpl(notificationRepository))
        following.notifyObservers()
        return followingRepository.save(following)
    }
    fun delete(followerId: Long, followedId: Long){
        val exist = followingRepository.findByFollowerIdAndFollowedId(followerId, followedId)
        followingRepository.delete(exist)
    }
}