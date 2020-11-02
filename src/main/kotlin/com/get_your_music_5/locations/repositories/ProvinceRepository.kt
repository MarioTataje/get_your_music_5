package com.get_your_music_5.locations.repositories

import com.get_your_music_5.locations.models.Province
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProvinceRepository : JpaRepository<Province, Long>{
    fun getByRegionId(regionId: Long): List<Province>
}