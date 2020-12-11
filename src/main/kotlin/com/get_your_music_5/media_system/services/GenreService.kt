package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.repositories.GenreRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service

@Service
class GenreService(
        private val genreRepository: GenreRepository,
        private val musicianRepository: MusicianRepository
) {
    fun getAll(): List<Genre> = genreRepository.findAll()

    fun getAllByMusician(musicianId: Long): MutableList<Genre> {
        val musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return musician.genres
    }

    fun getById(id: Long): Genre?= genreRepository.findById(id)
            .orElseThrow { NotFoundException("Genre", "id", id) }
}