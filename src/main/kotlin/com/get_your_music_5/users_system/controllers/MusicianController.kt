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
    @GetMapping("/musicians")
    fun getAllMusicians(): ResponseEntity<List<MusicianResource>> {
        return try{
            val musicians: List<Musician> = musicianService.getAll()
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/genres/{genreId}/musicians")
    fun getAllMusiciansByGenreId(@PathVariable genreId: Long): ResponseEntity<List<MusicianResource>>{
        return try{
            val musicians = musicianService.getAllByGenreId(genreId)
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/instruments/{instrumentId}/musicians")
    fun getAllMusiciansByInstrumentId(@PathVariable instrumentId: Long): ResponseEntity<List<MusicianResource>>{
        return try{
            val musicians = musicianService.getAllByInstrumentId(instrumentId)
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/districts/{districtId}/musicians")
    fun getAllMusiciansByDistrictId(@PathVariable districtId: Long): ResponseEntity<List<MusicianResource>> {
        return try{
            val musicians = musicianService.getAllByDistrictId(districtId)
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}")
    fun getMusicianById(@PathVariable musicianId: Long): ResponseEntity<MusicianResource> {
        return try{
            val existed = musicianService.getById(musicianId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/musicians/{musicianId}/genres/{genreId}")
    fun addGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        musicianService.addGenreToMusician(musicianId, genreId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/musicians/{musicianId}/genres/{genreId}")
    fun deleteGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        musicianService.deleteGenreToMusician(musicianId, genreId)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/musicians/{musicianId}/instruments/{instrumentId}")
    fun addInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long)
            : ResponseEntity<*>{
        musicianService.addInstrumentToMusician(musicianId, instrumentId)
        return ResponseEntity<Any>(HttpStatus.ACCEPTED)
    }

    @DeleteMapping("/musicians/{musicianId}/instruments/{instrumentId}")
    fun deleteInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long):
            ResponseEntity<*>{
        musicianService.deleteInstrumentToMusician(musicianId, instrumentId)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
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