package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.repositories.GenreRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import org.springframework.stereotype.Service

@Service
class GenreService(
        private val genreRepository: GenreRepository,
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Genre> = genreRepository.findAll()

    fun getAllByMusicianId(musicianId: Long): MutableList<Genre> {
        return musicianRepository.findById(musicianId).map { return@map it.genres }
                .orElseThrow{ throw java.lang.IllegalArgumentException("Musician not found $musicianId") }
    }
}