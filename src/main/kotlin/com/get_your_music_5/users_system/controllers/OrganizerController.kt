package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.Organizer
import com.get_your_music_5.users_system.resources.OrganizerResource
import com.get_your_music_5.users_system.services.OrganizerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class OrganizerController(
        private val organizerService: OrganizerService
) {
    @GetMapping("/organizers/")
    fun getAllOrganizers(): ResponseEntity<List<OrganizerResource>> {
        return try{
            val organizers: List<Organizer> = organizerService.getAll()
            if (organizers.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(organizers), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/organizers/{organizerId}/")
    fun getOrganizerById(@PathVariable organizerId: Long): ResponseEntity<OrganizerResource> {
        return try{
            val existed = organizerService.getById(organizerId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResourceList(entities: List<Organizer>) : List<OrganizerResource>{
        val resources = mutableListOf<OrganizerResource>()
        for(entity in entities){
            val resource = OrganizerResource(
                    id = entity.id,
                    firstName = entity.firstName,
                    lastName = entity.lastName,
                    birthDate = entity.birthDate,
                    phone = entity.phone,
                    description = entity.description,
                    registerDate = entity.registerDate,
                    photoUrl = entity.photoUrl,
                    type = entity.type,
                    userEmail = entity.user?.email,
                    districtName = entity.district?.name
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Organizer) = OrganizerResource(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            birthDate = entity.birthDate,
            phone = entity.phone,
            description = entity.description,
            registerDate = entity.registerDate,
            photoUrl = entity.photoUrl,
            type = entity.type,
            userEmail = entity.user?.email,
            districtName = entity.district?.name
    )
}