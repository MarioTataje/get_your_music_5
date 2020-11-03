package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.resources.MusicianResource
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class MusicianController(
        private val musicianService: MusicianService
){
    @GetMapping("/musicians")
    fun getAllMusicians(): List<MusicianResource> {
        val musicians = musicianService.getAll()
        return toResourceList(musicians)
    }

    @GetMapping("/districts/{districtId}/musicians")
    fun getAllMusiciansByDistrictId(@PathVariable districtId: Long): List<MusicianResource> {
        val musicians = musicianService.getAllByDistrictId(districtId)
        return toResourceList(musicians)
    }

    @GetMapping("/musicians/{musicianId}")
    fun getMusicianById(@PathVariable musicianId: Long): MusicianResource {
        val existed = musicianService.getById(musicianId)
        return toResource(existed)
    }

    fun toResourceList(entities: List<Musician>) : List<MusicianResource>{
        val resources = mutableListOf<MusicianResource>()
        for(entity in entities){
            val resource = MusicianResource(
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
                    districtName = entity.district?.name,
                    rating = entity.rating
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Musician) = MusicianResource(
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
            districtName = entity.district?.name,
            rating = entity.rating
    )
}