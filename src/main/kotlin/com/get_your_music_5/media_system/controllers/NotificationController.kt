package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Notification
import com.get_your_music_5.media_system.resources.NotificationResource
import com.get_your_music_5.media_system.services.NotificationService
import com.get_your_music_5.users_system.services.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class NotificationController(
        private val notificationService: NotificationService,
        private val profileService: ProfileService
) {
    @GetMapping("/profiles/{profileId}/notifications")
    fun getAllNotificationsByProfile(@PathVariable profileId: Long): ResponseEntity<List<NotificationResource>> {
        return try{
            profileService.getById(profileId)?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val notifications = notificationService.getAllByProfileId(profileId)
            if (notifications.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(notifications.map { notification -> this.toResource(notification) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResource(entity: Notification) = NotificationResource(
            id = entity.id,
            message = entity.message,
            profileName = entity.profile?.firstName
    )
}