package com.get_your_music_5.media_system.controllers

import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.resources.GenreResource
import com.get_your_music_5.media_system.services.GenreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class GenreController(
        private val genreService: GenreService
) {
    @GetMapping("/genres")
    fun getAllGenres(): List<GenreResource> {
        val genres = genreService.getAll()
        return toResourceList(genres)
    }

    @GetMapping("/musicians/{musicianId}/genres")
    fun getAllGenresByMusicianId(@PathVariable musicianId: Long): List<GenreResource>{
        val genres = genreService.getAllByMusicianId(musicianId)
        return toResourceList(genres)
    }

    fun toResourceList(entities: List<Genre>) : List<GenreResource>{
        val resources = mutableListOf<GenreResource>()
        for(entity in entities){
            val resource = GenreResource(entity.id, entity.name)
            resources.add(resource)
        }
        return resources
    }
}