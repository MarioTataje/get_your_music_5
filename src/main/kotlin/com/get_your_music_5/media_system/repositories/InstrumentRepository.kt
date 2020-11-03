package com.get_your_music_5.media_system.repositories

import com.get_your_music_5.media_system.models.Instrument
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InstrumentRepository: JpaRepository<Instrument, Long>