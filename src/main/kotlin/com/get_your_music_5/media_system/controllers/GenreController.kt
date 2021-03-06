package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.resources.GenreResource
import com.get_your_music_5.media_system.services.GenreService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class GenreController(
        private val genreService: GenreService
) {
    @GetMapping("/genres/")
    fun getAllGenres(): ResponseEntity<List<GenreResource>> {
        return try{
            val genres = genreService.getAll()
            if (genres.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(genres.map { genre -> this.toResource(genre) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/genres/")
    fun getAllGenresByMusician(@PathVariable musicianId: Long): ResponseEntity<List<GenreResource>>{
        val genres = genreService.getAllByMusician(musicianId)
        if (genres.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(genres.map { genre -> this.toResource(genre) }, HttpStatus.OK)
    }

    fun toResource(entity: Genre) = GenreResource(
            id = entity.id,
            name = entity.name
    )
}