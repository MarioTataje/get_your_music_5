package com.get_your_music_5.users_system.services

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MusicianService(
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Musician> = musicianRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Musician> = musicianRepository.getByDistrictId(districtId)

    fun getAllByGenre(genre: Genre): MutableList<Musician> {
        return genre.musicians
    }

    fun getAllByInstrument(instrument: Instrument): MutableList<Musician> {
        return instrument.musicians
    }

    fun getById(musicianId: Long): Musician? {
        return musicianRepository.findById(musicianId).orElse(null)
    }

    @Transactional
    fun addGenreToMusician(musician: Musician, genre: Genre){
        if(!musician.genres.contains(genre)) {
            musician.genres.add(genre)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun deleteGenreToMusician(musician: Musician, genre: Genre){
        if(musician.genres.contains(genre)){
            musician.genres.remove(genre)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun addInstrumentToMusician(musician: Musician, instrument: Instrument){
        if(!musician.instruments.contains(instrument)){
            musician.instruments.add(instrument)
            musicianRepository.save(musician)
        }
    }

    @Transactional
    fun deleteInstrumentToMusician(musician: Musician, instrument: Instrument){
        if(musician.instruments.contains(instrument)){
            musician.instruments.remove(instrument)
            musicianRepository.save(musician)
        }
    }

}