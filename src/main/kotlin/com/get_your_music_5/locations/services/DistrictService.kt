package com.get_your_music_5.locations.services

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.locations.repositories.ProvinceRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service

@Service
class DistrictService(
        private val districtRepository: DistrictRepository,
        private val provinceRepository: ProvinceRepository
) {
    fun getAllByProvinceId(provinceId: Long): List<District> {
        provinceRepository.findById(provinceId).orElseThrow { NotFoundException("Province", "id", provinceId) }
        return districtRepository.getByProvinceId(provinceId)
    }

    fun getById(id: Long): District? = districtRepository.findById(id).orElse(null)
}