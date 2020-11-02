package com.get_your_music_5.locations.repositories

import com.get_your_music_5.locations.models.District
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DistrictRepository : JpaRepository<District, Long> {
    fun getByProvinceId(provinceId: Long): List<District>
}