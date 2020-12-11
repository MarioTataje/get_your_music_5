package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service

@Service
class InstrumentService (
        private val instrumentRepository: InstrumentRepository,
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Instrument> = instrumentRepository.findAll()

    fun getAllByMusician(musicianId: Long): MutableList<Instrument> {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return musician.instruments
    }

    fun getById(id: Long): Instrument = instrumentRepository.findById(id)
            .orElseThrow { NotFoundException("Instrument", "id", id) }
}