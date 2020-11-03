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

    fun getById(musicianId: Long): Musician {
        return musicianRepository.findById(musicianId)
                .orElseThrow { throw IllegalArgumentException("Musician not found $musicianId") }
    }

    @Transactional
    fun addGenreToMusician(musicianId: Long, genreId: Long){
        val existedGenre = genreRepository.findById(genreId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Genre not found $genreId") }
        musicianRepository.findById(musicianId).map{
            if(!it.genres.contains(existedGenre)){
                it.genres.add(existedGenre)
                musicianRepository.save(it)
            }
        }.orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }

    @Transactional
    fun deleteGenreToMusician(musicianId: Long, genreId: Long){
        val existedGenre = genreRepository.findById(genreId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Genre not found $genreId") }
        musicianRepository.findById(musicianId).map {
            if(it.genres.contains(existedGenre)){
                it.genres.remove(existedGenre)
                musicianRepository.save(it)
            }
        }.orElseThrow { throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }

    @Transactional
    fun addInstrumentToMusician(musicianId: Long, instrumentId: Long){
        val existedInstrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Instrument not found $instrumentId") }
        musicianRepository.findById(musicianId).map{
            if(!it.instruments.contains(existedInstrument)){
                it.instruments.add(existedInstrument)
                musicianRepository.save(it)
            }
        }.orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }

    @Transactional
    fun deleteInstrumentToMusician(musicianId: Long, instrumentId: Long){
        val existedInstrument = instrumentRepository.findById(instrumentId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Instrument not found $instrumentId") }
        musicianRepository.findById(musicianId).map {
            if(it.instruments.contains(existedInstrument)){
                it.instruments.remove(existedInstrument)
                musicianRepository.save(it)
            }
        }.orElseThrow { throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }

}