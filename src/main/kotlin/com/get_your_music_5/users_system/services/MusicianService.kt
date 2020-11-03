package com.get_your_music_5.users_system.services

import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service

@Service
class MusicianService(
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Musician> = musicianRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Musician> = musicianRepository.getByDistrictId(districtId)

    fun getById(musicianId: Long): Musician {
        return musicianRepository.findById(musicianId)
                .orElseThrow { throw IllegalArgumentException("Musician not found $musicianId") }
    }
}