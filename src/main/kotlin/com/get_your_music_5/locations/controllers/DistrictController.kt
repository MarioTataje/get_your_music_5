package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.locations.resources.DistrictResource
import com.get_your_music_5.locations.services.DistrictService
import com.get_your_music_5.locations.services.ProvinceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DistrictController(
        private val districtService: DistrictService,
        private val provinceService: ProvinceService
) {
    @GetMapping("/provinces/{provinceId}/districts/")
    fun getAllDistrictsByProvinceId(@PathVariable provinceId: Long): ResponseEntity<List<DistrictResource>> {
        return try {
            provinceService.getById(provinceId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val districts: List<District> = districtService.getAllByProvinceId(provinceId)
            if (districts.isEmpty()) {
                return ResponseEntity(HttpStatus.NO_CONTENT)
            }
            ResponseEntity(toResourceList(districts), HttpStatus.OK)
        } catch (e: Exception) {
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toResourceList(entities: List<District>) : List<DistrictResource>{
        val resources = mutableListOf<DistrictResource>()
        for(entity in entities){
            val resource = DistrictResource(entity.id, entity.name, entity.province?.name)
            resources.add(resource)
        }
        return resources
    }
}