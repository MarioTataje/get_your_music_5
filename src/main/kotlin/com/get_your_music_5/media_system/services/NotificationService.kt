package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Notification
import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.users_system.repositories.ProfileRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository,
        private val profileRepository: ProfileRepository
) {
    fun getAllByProfileId(profileId: Long): List<Notification> {
        profileRepository.findById(profileId).orElseThrow { NotFoundException("Profile", "id", profileId) }
        return notificationRepository.getByProfileId(profileId)
    }
}