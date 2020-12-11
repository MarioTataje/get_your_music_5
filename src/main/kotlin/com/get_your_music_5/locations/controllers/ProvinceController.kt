package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.Province
import com.get_your_music_5.locations.resources.ProvinceResource
import com.get_your_music_5.locations.services.ProvinceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProvinceController(
        private val provinceService: ProvinceService
) {
    @GetMapping("/regions/{regionId}/provinces/")
    fun getAllProvincesByRegionId(@PathVariable regionId: Long): ResponseEntity<List<ProvinceResource>> {
        return try {
            val provinces: List<Province> = provinceService.getAllByRegionId(regionId)
            if (provinces.isEmpty()) {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            ResponseEntity(provinces.map { province -> this.toResource(province)}, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResource(entity: Province) = ProvinceResource(
            id = entity.id,
            name = entity.name,
            regionName = entity.region?.name
    )
}