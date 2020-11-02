package com.get_your_music_5.locations.services;

import com.get_your_music_5.locations.repositories.RegionRepository
import com.get_your_music_5.locations.models.Region
import org.springframework.stereotype.Service;

@Service
class RegionService(
        private val regionRepository: RegionRepository
) {
    fun getAll(): List<Region> = regionRepository.findAll()
}
