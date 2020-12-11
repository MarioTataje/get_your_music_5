package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.locations.resources.DistrictResource
import com.get_your_music_5.locations.services.DistrictService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DistrictController(
        private val districtService: DistrictService
) {
    @GetMapping("/provinces/{provinceId}/districts/")
    fun getAllDistrictsByProvinceId(@PathVariable provinceId: Long): ResponseEntity<List<DistrictResource>> {
        return try {
            val districts: List<District> = districtService.getAllByProvinceId(provinceId)
            if (districts.isEmpty()) {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            ResponseEntity(districts.map { district -> this.toResource(district)  }, HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResource(entity: District) = DistrictResource(
            id = entity.id,
            name = entity.name,
            provinceName = entity.province?.name
    )
}