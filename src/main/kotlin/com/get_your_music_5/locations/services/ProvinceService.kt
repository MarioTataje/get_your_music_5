package com.get_your_music_5.locations.services

import com.get_your_music_5.locations.models.Province
import com.get_your_music_5.locations.repositories.ProvinceRepository
import com.get_your_music_5.locations.repositories.RegionRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service

@Service
class ProvinceService(
        private val provinceRepository: ProvinceRepository,
        private val regionRepository: RegionRepository
) {
    fun getAllByRegionId(regionId: Long): List<Province> {
        regionRepository.findById(regionId).orElseThrow { NotFoundException("Region", "id", regionId) }
        return provinceRepository.getByRegionId(regionId)
    }

    fun getById(id: Long): Province? = provinceRepository.findById(id).orElse(null)
}