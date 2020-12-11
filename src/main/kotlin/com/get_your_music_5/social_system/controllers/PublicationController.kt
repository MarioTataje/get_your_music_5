package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.resources.PublicationResource
import com.get_your_music_5.social_system.resources.SavePublicationResource
import com.get_your_music_5.social_system.services.PublicationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class PublicationController(
        private val publicationService: PublicationService
){

    @GetMapping("/publications/")
    fun getAllPublications(): ResponseEntity<List<PublicationResource>> {
        return try{
            val publications = publicationService.getAll()
            if (publications.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(publications.map{ publication -> this.toResource(publication)  }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/publications/")
    fun getAllPublicationsByMusicianId(@PathVariable musicianId: Long) : ResponseEntity<List<PublicationResource>> {
        val publications = publicationService.getAllByMusicianId(musicianId)
        if (publications.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(publications.map{ publication -> this.toResource(publication)  }, HttpStatus.OK)
    }

    @GetMapping("/publications/{publicationId}/")
    fun getPublicationById(@PathVariable publicationId: Long): ResponseEntity<PublicationResource> {
        val existed = publicationService.getById(publicationId)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    @PostMapping("/musicians/{musicianId}/publications/")
    fun create(@RequestBody publication: SavePublicationResource, @PathVariable musicianId: Long)
            : ResponseEntity<PublicationResource> {
        val newPublication = publicationService.save(toEntity(publication), musicianId)
        return ResponseEntity(toResource(newPublication), HttpStatus.OK)
    }

    @PutMapping("/publications/{id}/")
    fun update(@PathVariable id: Long, @RequestBody publication: SavePublicationResource):
            ResponseEntity<PublicationResource> {
        val existed = publicationService.update(id, toEntity(publication))
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    fun toEntity(resource: SavePublicationResource) = Publication(
            videoUrl = resource.videoUrl,
            content = resource.content
    )

    fun toResource(entity: Publication) = PublicationResource(
            id = entity.id,
            videoUrl = entity.videoUrl,
            content = entity.content,
            publishDate = entity.publishDate,
            musicianName = entity.musician?.firstName
    )
}