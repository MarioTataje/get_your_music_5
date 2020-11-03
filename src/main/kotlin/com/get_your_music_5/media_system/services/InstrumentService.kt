package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service

@Service
class InstrumentService (
        private val instrumentRepository: InstrumentRepository,
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Instrument> = instrumentRepository.findAll()

    fun getAllByMusicianId(musicianId: Long): MutableList<Instrument> {
        return musicianRepository.findById(musicianId).map { return@map it.instruments }
                .orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }
}