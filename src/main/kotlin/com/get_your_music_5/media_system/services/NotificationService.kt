package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Notification
import com.get_your_music_5.media_system.repositories.NotificationRepository
import org.springframework.stereotype.Service

@Service
class NotificationService(
        private val notificationRepository: NotificationRepository
) {
    fun getAllByProfileId(profileId: Long): List<Notification> = notificationRepository.getByProfileId(profileId)
}