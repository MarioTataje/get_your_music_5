package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.resources.MusicianResource
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.web.bind.annotation.*


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

    @GetMapping("/genres/{genreId}/musicians")
    fun getAllMusiciansByGenreId(@PathVariable genreId: Long): List<MusicianResource>{
        val musicians = musicianService.getAllByGenreId(genreId)
        return toResourceList(musicians)
    }

    @GetMapping("/instruments/{instrumentId}/musicians")
    fun getAllMusiciansByInstrumentId(@PathVariable instrumentId: Long): List<MusicianResource>{
        val musicians = musicianService.getAllByInstrumentId(instrumentId)
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

    @PostMapping("/musician/{musicianId}/genres/{genreId}")
    fun addGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long){
        musicianService.addGenreToMusician(musicianId, genreId)
    }

    @DeleteMapping("/musician/{musicianId}/genres/{genreId}")
    fun deleteGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long){
        musicianService.deleteGenreToMusician(musicianId, genreId)
    }

    @PostMapping("/musician/{musicianId}/instruments/{instrumentId}")
    fun addInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long){
        musicianService.addInstrumentToMusician(musicianId, instrumentId)
    }

    @DeleteMapping("/musician/{musicianId}/instruments/{instrumentId}")
    fun deleteInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long){
        musicianService.deleteInstrumentToMusician(musicianId, instrumentId)
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