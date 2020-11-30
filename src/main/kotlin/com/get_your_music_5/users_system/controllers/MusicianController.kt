package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.locations.services.DistrictService
import com.get_your_music_5.media_system.services.GenreService
import com.get_your_music_5.media_system.services.InstrumentService
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.resources.MusicianResource
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class MusicianController(
        private val musicianService: MusicianService,
        private val districtService: DistrictService,
        private val genreService: GenreService,
        private val instrumentService: InstrumentService
){
    @GetMapping("/musicians/")
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

    @GetMapping("/genres/{genreId}/musicians/")
    fun getAllMusiciansByGenre(@PathVariable genreId: Long): ResponseEntity<List<MusicianResource>>{
        return try{
            val genre = genreService.getById(genreId)?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val musicians = musicianService.getAllByGenre(genre)
            if (musicians.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/instruments/{instrumentId}/musicians/")
    fun getAllMusiciansByInstrument(@PathVariable instrumentId: Long): ResponseEntity<List<MusicianResource>>{
        return try{
            val instrument = instrumentService.getById(instrumentId)?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val musicians = musicianService.getAllByInstrument(instrument)
            if (musicians.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/districts/{districtId}/musicians/")
    fun getAllMusiciansByDistrictId(@PathVariable districtId: Long): ResponseEntity<List<MusicianResource>> {
        return try{
            districtService.getById(districtId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val musicians = musicianService.getAllByDistrictId(districtId)
            if (musicians.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(musicians), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/")
    fun getMusicianById(@PathVariable musicianId: Long): ResponseEntity<MusicianResource> {
        return try{
            val existed = musicianService.getById(musicianId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/musicians/{musicianId}/genres/{genreId}/")
    fun addGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        return try{
            val genre = genreService.getById(genreId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            val musician = musicianService.getById(musicianId)?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            musicianService.addGenreToMusician(musician, genre)
            return ResponseEntity<Any>(HttpStatus.ACCEPTED)
        } catch (e: Exception){
            ResponseEntity<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/musicians/{musicianId}/genres/{genreId}/")
    fun deleteGenreToMusician(@PathVariable musicianId: Long, @PathVariable genreId: Long): ResponseEntity<*>{
        return try{
            val genre = genreService.getById(genreId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            val musician = musicianService.getById(musicianId)?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            musicianService.deleteGenreToMusician(musician, genre)
            return ResponseEntity<Any>(HttpStatus.ACCEPTED)
        } catch (e: Exception){
            ResponseEntity<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/musicians/{musicianId}/instruments/{instrumentId}/")
    fun addInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long)
            : ResponseEntity<*>{
        return try{
            val instrument = instrumentService.getById(instrumentId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            val musician = musicianService.getById(musicianId)?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            musicianService.addInstrumentToMusician(musician, instrument)
            return ResponseEntity<Any>(HttpStatus.ACCEPTED)
        } catch (e: Exception){
            ResponseEntity<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @DeleteMapping("/musicians/{musicianId}/instruments/{instrumentId}/")
    fun deleteInstrumentToMusician(@PathVariable musicianId: Long, @PathVariable instrumentId: Long):
            ResponseEntity<*>{
        return try{
            val instrument = instrumentService.getById(instrumentId) ?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            val musician = musicianService.getById(musicianId)?: return ResponseEntity<Any>(HttpStatus.NOT_FOUND)
            musicianService.deleteInstrumentToMusician(musician, instrument)
            return ResponseEntity<Any>(HttpStatus.ACCEPTED)
        } catch (e: Exception){
            ResponseEntity<Any>(HttpStatus.INTERNAL_SERVER_ERROR)
        }
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