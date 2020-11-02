package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.locations.resources.DistrictResource
import com.get_your_music_5.locations.services.DistrictService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class DistrictController(
        private val districtService: DistrictService
) {
    @GetMapping("/provinces/{provinceId}/districts")
    fun getAllDistrictsByProvinceId(@PathVariable provinceId: Long): List<DistrictResource> {
        val districts = districtService.getAllByProvinceId(provinceId)
        return toResourceList(districts)
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