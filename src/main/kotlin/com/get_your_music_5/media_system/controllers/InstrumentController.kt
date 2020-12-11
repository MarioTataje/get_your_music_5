package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.resources.InstrumentResource
import com.get_your_music_5.media_system.services.InstrumentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class InstrumentController(
        private val instrumentService: InstrumentService
) {
    @GetMapping("/instruments/")
    fun getAllInstruments(): ResponseEntity<List<InstrumentResource>> {
        return try{
            val instruments = instrumentService.getAll()
            if (instruments.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(instruments.map { instrument -> this.toResource(instrument) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/instruments")
    fun getAllInstrumentsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<InstrumentResource>>{
        val instruments = instrumentService.getAllByMusician(musicianId)
        if (instruments.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(instruments.map { instrument -> this.toResource(instrument) }, HttpStatus.OK)
    }

    fun toResource(entity: Instrument)= InstrumentResource(
            id = entity.id,
            name = entity.name
    )
}