package com.get_your_music_5.locations.repositories

import com.get_your_music_5.locations.models.Region
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RegionRepository : JpaRepository<Region, Long>