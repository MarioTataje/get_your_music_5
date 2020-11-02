package com.get_your_music_5.locations.controllers

import com.get_your_music_5.locations.models.Province
import com.get_your_music_5.locations.resources.ProvinceResource
import com.get_your_music_5.locations.services.ProvinceService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class ProvinceController(
        private val provinceService: ProvinceService
) {
    @GetMapping("/regions/{regionId}/provinces")
    fun getAllProvincesByRegionId(@PathVariable regionId: Long): List<ProvinceResource> {
        val provinces = provinceService.getAllByRegionId(regionId)
        return toResourceList(provinces)
    }

    fun toResourceList(entities: List<Province>) : List<ProvinceResource>{
        val resources = mutableListOf<ProvinceResource>()
        for(entity in entities){
            val resource = ProvinceResource(entity.id, entity.name, entity.region?.name)
            resources.add(resource)
        }
        return resources
    }
}