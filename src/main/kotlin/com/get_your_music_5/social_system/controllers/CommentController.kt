package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.resources.CommentResource
import com.get_your_music_5.social_system.resources.SaveCommentResource
import com.get_your_music_5.social_system.services.CommentService
import com.get_your_music_5.social_system.services.PublicationService
import com.get_your_music_5.users_system.services.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class CommentController(
        private val commentService: CommentService,
        private val publicationService: PublicationService,
        private val profileService: ProfileService
){

    @GetMapping("/publications/{publicationId}/comments/")
    fun getAllCommentsByPublicationId(@PathVariable publicationId: Long) : ResponseEntity<List<CommentResource>> {
        return try{
            publicationService.getById(publicationId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val comments = commentService.getAllByPublicationId(publicationId)
            if (comments.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(comments.map { comment -> this.toResource(comment) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/publications/{publicationId}/commentators/{commenterId}/comments/")
    fun create(@RequestBody comment: SaveCommentResource,
               @PathVariable publicationId: Long, @PathVariable commenterId: Long) : ResponseEntity<CommentResource> {
        return try{
            val publication = publicationService.getById(publicationId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val commenter = profileService.getById(commenterId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newComment = commentService.save(toEntity(comment), publication, commenter)
            ResponseEntity(toResource(newComment), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toEntity(resource: SaveCommentResource) = Comment(
            text = resource.text
    )

    fun toResource(entity: Comment) = CommentResource(
            id = entity.id,
            text = entity.text,
            commenterName = entity.commenter?.firstName,
            publicationContent = entity.publication?.content
    )
}