package com.get_your_music_5.media_system.services

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.repositories.GenreRepository
import org.springframework.stereotype.Service

@Service
class GenreService(
        private val genreRepository: GenreRepository
) {
    fun getAll(): List<Genre> = genreRepository.findAll()
}