package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Notification
import com.get_your_music_5.media_system.resources.NotificationResource
import com.get_your_music_5.media_system.services.NotificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class NotificationController(
        private val notificationService: NotificationService
) {
    @GetMapping("/profiles/{profileId}/notifications")
    fun getAllInstruments(@PathVariable profileId: Long): List<NotificationResource> {
        val notifications = notificationService.getAllByProfileId(profileId)
        return toResourceList(notifications)
    }

    fun toResourceList(entities: List<Notification>) : List<NotificationResource>{
        val resources = mutableListOf<NotificationResource>()
        for(entity in entities){
            val resource = NotificationResource(entity.id, entity.message, entity.profile?.firstName)
            resources.add(resource)
        }
        return resources
    }
}