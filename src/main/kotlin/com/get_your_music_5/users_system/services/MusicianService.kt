package com.get_your_music_5.users_system.services

import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.media_system.repositories.GenreRepository
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MusicianService(
        private val musicianRepository: MusicianRepository,
        private val districtRepository: DistrictRepository,
        private val genreRepository: GenreRepository,
        private val instrumentRepository: InstrumentRepository
) {
    fun getAll(): List<Musician> = musicianRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Musician> {
        districtRepository.findById(districtId).orElseThrow { NotFoundException("District", "id", districtId) }
        return musicianRepository.getByDistrictId(districtId)
    }

    fun getAllByGenre(genreId: Long): MutableList<Musician> {
        val genre = genreRepository.findById(genreId).orElseThrow { NotFoundException("Genre", "id", genreId) }
        return genre.musicians
    }

    fun getAllByInstrument(instrumentId: Long): MutableList<Musician> {
        val instrument = instrumentRepository.
        findById(instrumentId).orElseThrow { NotFoundException("Instrument", "id", instrumentId) }
        return instrument.musicians
    }

    fun getById(id: Long): Musician = musicianRepository
            .findById(id).orElseThrow { NotFoundException("Musician", "id", id) }

    @Transactional
    fun addGenreToMusician(musicianId: Long, genreId: Long) {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        val genre = genreRepository.findById(genreId).
        orElseThrow { NotFoundException("Genre", "id", genreId) }
        if (!musician.genres.contains(genre)) {
            musician.genres.add(genre)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun deleteGenreToMusician(musicianId: Long, genreId: Long) {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        val genre = genreRepository.findById(genreId).
        orElseThrow { NotFoundException("Genre", "id", genreId) }
        if (musician.genres.contains(genre)) {
            musician.genres.remove(genre)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun addInstrumentToMusician(musicianId: Long, instrumentId: Long) {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        val instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { NotFoundException("Instrument", "id", instrumentId) }
        if (!musician.instruments.contains(instrument)) {
            musician.instruments.add(instrument)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun deleteInstrumentToMusician(musicianId: Long, instrumentId: Long) {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        val instrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { NotFoundException("Instrument", "id", instrumentId) }
        if (musician.instruments.contains(instrument)) {
            musician.instruments.remove(instrument)
            musicianRepository.save(musician)
        }
    }
}