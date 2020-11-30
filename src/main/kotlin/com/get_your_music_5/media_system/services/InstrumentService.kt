package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import com.get_your_music_5.users_system.models.Musician
import org.springframework.stereotype.Service

@Service
class InstrumentService (
        private val instrumentRepository: InstrumentRepository
) {
    fun getAll(): List<Instrument> = instrumentRepository.findAll()

    fun getAllByMusician(musician: Musician): MutableList<Instrument> {
        return musician.instruments
    }

    fun getById(id: Long): Instrument? = instrumentRepository.findById(id).orElse(null)
}