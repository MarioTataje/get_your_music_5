package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.Region
import com.get_your_music_5.locations.resources.RegionResource
import com.get_your_music_5.locations.services.RegionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class RegionController(
        private val regionService: RegionService
) {
    @GetMapping("/regions/")
    fun getAllRegions(): ResponseEntity<List<RegionResource>> {
        return try {
            val regions = regionService.getAll()
            if (regions.isEmpty()) {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }

            ResponseEntity(regions.map { region -> this.toResource(region)}, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResource(entity: Region) = RegionResource(
            id = entity.id,
            name = entity.name
    )
}