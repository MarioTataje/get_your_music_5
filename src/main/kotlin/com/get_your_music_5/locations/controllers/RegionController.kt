package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.Region
import com.get_your_music_5.locations.resources.RegionResource
import com.get_your_music_5.locations.services.RegionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class RegionController(
        private val regionService: RegionService
) {
    @GetMapping("/regions")
    fun getAllRegions(): List<RegionResource> {
        val regions = regionService.getAll()
        return toResourceList(regions)
    }

    fun toResourceList(entities: List<Region>) : List<RegionResource>{
        val resources = mutableListOf<RegionResource>()
        for(entity in entities){
            val resource = RegionResource(entity.id, entity.name)
            resources.add(resource)
        }
        return resources
    }
}