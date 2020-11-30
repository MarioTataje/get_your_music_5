package com.get_your_music_5.locations.services

import com.get_your_music_5.locations.models.Province
import com.get_your_music_5.locations.repositories.ProvinceRepository
import org.springframework.stereotype.Service

@Service
class ProvinceService(
        private val provinceRepository: ProvinceRepository
) {
    fun getAllByRegionId(regionId: Long): List<Province> = provinceRepository.getByRegionId(regionId)

    fun getById(id: Long): Province? = provinceRepository.findById(id).orElse(null)
}