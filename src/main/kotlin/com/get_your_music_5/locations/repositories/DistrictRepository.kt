package com.get_your_music_5.locations.repositories

import com.get_your_music_5.locations.models.District
import org.springframework.data.jpa.repository.JpaRepository

interface DistrictRepository : JpaRepository<District, Long> {
    fun getByProvinceId(provinceId: Long): List<District>
}