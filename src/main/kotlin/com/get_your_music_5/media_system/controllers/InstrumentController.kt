package com.get_your_music_5.media_system.controllers


import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.resources.InstrumentResource
import com.get_your_music_5.media_system.services.InstrumentService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class InstrumentController(
        private val instrumentService: InstrumentService
) {
    @GetMapping("/instruments")
    fun getAllInstruments(): List<InstrumentResource> {
        val instruments = instrumentService.getAll()
        return toResourceList(instruments)
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