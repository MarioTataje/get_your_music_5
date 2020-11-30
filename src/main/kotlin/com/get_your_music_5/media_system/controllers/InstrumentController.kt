package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.resources.InstrumentResource
import com.get_your_music_5.media_system.services.InstrumentService
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class InstrumentController(
        private val instrumentService: InstrumentService,
        private val musicianService: MusicianService
) {
    @GetMapping("/instruments/")
    fun getAllInstruments(): ResponseEntity<List<InstrumentResource>> {
        return try{
            val instruments = instrumentService.getAll()
            if (instruments.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(instruments), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/instruments")
    fun getAllInstrumentsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<InstrumentResource>>{
        return try{
            val musician = musicianService.getById(musicianId)?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val instruments = instrumentService.getAllByMusician(musician)
            if (instruments.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(instruments), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResourceList(entities: List<Instrument>) : List<InstrumentResource>{
        val resources = mutableListOf<InstrumentResource>()
        for(entity in entities){
            val resource = InstrumentResource(entity.id, entity.name)
            resources.add(resource)
        }
        return resources
    }
}