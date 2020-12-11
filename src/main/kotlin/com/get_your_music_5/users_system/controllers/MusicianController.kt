package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.resources.MusicianResource
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MusicianController(
        private val musicianService: MusicianService
){
    @GetMapping("/musicians/")
    fun getAllMusicians(): ResponseEntity<List<MusicianResource>> {
        return try{
            val musicians: List<Musician> = musicianService.getAll()
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(musicians.map { musician -> this.toResource(musician) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/genres/{genreId}/musicians/")
    fun getAllMusiciansByGenre(@PathVariable genreId: Long): ResponseEntity<List<MusicianResource>>{
        val musicians = musicianService.getAllByGenre(genreId)
        if (musicians.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(musicians.map { musician -> this.toResource(musician) }, HttpStatus.OK)
    }

    @GetMapping("/instruments/{instrumentId}/musicians/")
    fun getAllMusiciansByInstrument(@PathVariable instrumentId: Long): ResponseEntity<List<MusicianResource>>{
        val musicians = musicianService.getAllByInstrument(instrumentId)
        if (musicians.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(musicians.map { musician -> this.toResource(musician) }, HttpStatus.OK)
    }

    @GetMapping("/districts/{districtId}/musicians/")
    fun getAllMusiciansByDistrictId(@PathVariable districtId: Long): ResponseEntity<List<MusicianResource>> {
        val musicians = musicianService.getAllByDistrictId(districtId)
        if (musicians.isEmpty())
            return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(musicians.map { musician -> this.toResource(musician) }, HttpStatus.OK)
    }

    @GetMapping("/musicians/{id}/")
    fun getMusicianById(@PathVariable id: Long): ResponseEntity<MusicianResource> {
        val existed = musicianService.getById(id)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    @PostMapping("/musicians/{musicianId}/genres/{genreId}/")
    fun addGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        musicianService.addGenreToMusician(musicianId, genreId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/musicians/{musicianId}/genres/{genreId}/")
    fun deleteGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        musicianService.deleteGenreToMusician(musicianId, genreId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
    }

    @PostMapping("/musicians/{musicianId}/instruments/{instrumentId}/")
    fun addInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long)
            : ResponseEntity<*>{
        musicianService.addInstrumentToMusician(musicianId, instrumentId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/musicians/{musicianId}/instruments/{instrumentId}/")
    fun deleteInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long):
            ResponseEntity<*>{
        musicianService.deleteInstrumentToMusician(musicianId, instrumentId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
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