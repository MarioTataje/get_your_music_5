package com.get_your_music_5.users_system.repositories

import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.models.Profile
import org.springframework.data.jpa.repository.JpaRepository

interface MusicianRepository: JpaRepository<Musician, Long>{
    fun getByDistrictId(districtId: Long): List<Musician>
}