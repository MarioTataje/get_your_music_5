package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.resources.PublicationResource
import com.get_your_music_5.social_system.resources.SavePublicationResource
import com.get_your_music_5.social_system.services.PublicationService
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class PublicationController(
        private val publicationService: PublicationService,
        private val musicianService: MusicianService
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
        return try{
            musicianService.getById(musicianId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val publications = publicationService.getAllByMusicianId(musicianId)
            if (publications.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(publications.map{ publication -> this.toResource(publication)  }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/publications/{publicationId}/")
    fun getPublicationById(@PathVariable publicationId: Long): ResponseEntity<PublicationResource> {
        return try{
            val existed = publicationService.getById(publicationId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/musicians/{musicianId}/publications/")
    fun create(@RequestBody publication: SavePublicationResource, @PathVariable musicianId: Long)
            : ResponseEntity<PublicationResource> {
        return try{
            val musician = musicianService.getById(musicianId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newPublication = publicationService.save(toEntity(publication), musician)
            ResponseEntity(toResource(newPublication), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/publications/{publicationId}/")
    fun update(@PathVariable publicationId: Long, @RequestBody publication: SavePublicationResource):
            ResponseEntity<PublicationResource> {
        return try{
            val existed = publicationService.getById(publicationId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val updated = publicationService.update(existed, toEntity(publication))
            ResponseEntity(toResource(updated), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
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