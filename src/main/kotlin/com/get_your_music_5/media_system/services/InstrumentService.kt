package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import org.springframework.stereotype.Service

@Service
class InstrumentService (
        private val instrumentRepository: InstrumentRepository
) {
    fun getAll(): List<Instrument> = instrumentRepository.findAll()
}