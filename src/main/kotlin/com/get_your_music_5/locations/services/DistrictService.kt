package com.get_your_music_5.locations.services

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.locations.repositories.DistrictRepository
import org.springframework.stereotype.Service

@Service
class DistrictService(
        private val districtRepository: DistrictRepository
) {
    fun getAllByProvinceId(provinceId: Long): List<District> = districtRepository.getByProvinceId(provinceId)
}