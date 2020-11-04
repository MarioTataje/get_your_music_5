package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.resources.PublicationResource
import com.get_your_music_5.social_system.resources.SavePublicationResource
import com.get_your_music_5.social_system.services.PublicationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class PublicationController(
        private val publicationService: PublicationService
){

    @GetMapping("/publications")
    fun getAllPublications(): List<PublicationResource> {
        val publications = publicationService.getAll()
        return toResourceList(publications)
    }

    @GetMapping("/musicians/{musicianId}/publications")
    fun getAllPublicationsByMusicianId(@PathVariable musicianId: Long) : List<PublicationResource> {
        val publications = publicationService.getAllByMusicianId(musicianId)
        return toResourceList(publications)
    }

    @GetMapping("/publications/{publicationId}")
    fun getPublicationById(@PathVariable publicationId: Long): PublicationResource {
        val existed = publicationService.getById(publicationId)
        return toResource(existed)
    }

    @PostMapping("/musicians/{musicianId}/publications")
    fun create(@RequestBody publication: SavePublicationResource, @PathVariable musicianId: Long) : PublicationResource {
        val newPublication = publicationService.save(toEntity(publication), musicianId)
        return toResource(newPublication)
    }

    @PutMapping("/publications/{publicationId}")
    fun update(@PathVariable publicationId: Long, @RequestBody publication: SavePublicationResource):
            PublicationResource {
        val existed = publicationService.update(publicationId, toEntity(publication))
        return toResource(existed)
    }

    fun toEntity(resource: SavePublicationResource) = Publication(
            videoUrl = resource.videoUrl,
            content = resource.content
    )

    fun toResourceList(entities: List<Publication>) : List<PublicationResource>{
        val resources = mutableListOf<PublicationResource>()
        for(entity in entities){
            val resource = PublicationResource(
                    id = entity.id,
                    videoUrl = entity.videoUrl,
                    content = entity.content,
                    publishDate = entity.publishDate,
                    musicianName = entity.musician?.firstName
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Publication) = PublicationResource(
            id = entity.id,
            videoUrl = entity.videoUrl,
            content = entity.content,
            publishDate = entity.publishDate,
            musicianName = entity.musician?.firstName
    )
}