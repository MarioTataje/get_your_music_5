package com.get_your_music_5.users_system.services

import com.get_your_music_5.media_system.repositories.GenreRepository
import com.get_your_music_5.media_system.repositories.InstrumentRepository
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MusicianService(
        private val musicianRepository: MusicianRepository,
        private val genreRepository: GenreRepository,
        private val instrumentRepository: InstrumentRepository
) {
    fun getAll(): List<Musician> = musicianRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Musician> = musicianRepository.getByDistrictId(districtId)

    fun getAllByGenreId(genreId: Long): MutableList<Musician> {
        return genreRepository.findById(genreId).map { return@map it.musicians }
                .orElseThrow{ throw java.lang.IllegalArgumentException("Genre not found $genreId") }
    }

    fun getAllByInstrumentId(instrumentId: Long): MutableList<Musician> {
        return instrumentRepository.findById(instrumentId).map { return@map it.musicians }
                .orElseThrow{ throw java.lang.IllegalArgumentException("Instrument not found $instrumentId") }
    }

    fun getById(musicianId: Long): Musician? {
        return musicianRepository.findById(musicianId).orElse(null)
    }

    @Transactional
    fun addGenreToMusician(musicianId: Long, genreId: Long){
        val existedGenre = genreRepository.findById(genreId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Genre not found $genreId") }
        val existedMusician = musicianRepository.findById(musicianId)
                .orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
        if(!existedMusician.genres.contains(existedGenre)) {
            existedMusician.genres.add(existedGenre)
            musicianRepository.save(existedMusician)
        }
    }

    @Transactional
    fun deleteGenreToMusician(musicianId: Long, genreId: Long){
        val existedGenre = genreRepository.findById(genreId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Genre not found $genreId") }
        val existedMusician = musicianRepository.findById(musicianId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
        if(existedMusician.genres.contains(existedGenre)){
            existedMusician.genres.remove(existedGenre)
            musicianRepository.save(existedMusician)
        }
    }

    @Transactional
    fun addInstrumentToMusician(musicianId: Long, instrumentId: Long){
        val existedInstrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Instrument not found $instrumentId") }
        val existedMusician = musicianRepository.findById(musicianId)
                .orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
        if(!existedMusician.instruments.contains(existedInstrument)){
            existedMusician.instruments.add(existedInstrument)
            musicianRepository.save(existedMusician)
        }
    }

    @Transactional
    fun deleteInstrumentToMusician(musicianId: Long, instrumentId: Long){
        val existedInstrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Instrument not found $instrumentId") }
        val existedMusician = musicianRepository.findById(musicianId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
        if(existedMusician.instruments.contains(existedInstrument)){
            existedMusician.instruments.remove(existedInstrument)
            musicianRepository.save(existedMusician)
        }
    }

}