package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.resources.CommentResource
import com.get_your_music_5.social_system.resources.SaveCommentResource
import com.get_your_music_5.social_system.services.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class CommentController(
        private val commentService: CommentService
){

    @GetMapping("/publications/{publicationId}/comments/")
    fun getAllCommentsByPublicationId(@PathVariable publicationId: Long) : ResponseEntity<List<CommentResource>> {
        val comments = commentService.getAllByPublicationId(publicationId)
        if (comments.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(comments.map { comment -> this.toResource(comment) }, HttpStatus.OK)
    }

    @PostMapping("/publications/{publicationId}/commentators/{commenterId}/comments/")
    fun create(@RequestBody comment: SaveCommentResource,
               @PathVariable publicationId: Long, @PathVariable commenterId: Long) : ResponseEntity<CommentResource> {
        val newComment = commentService.save(toEntity(comment), publicationId, commenterId)
        return ResponseEntity(toResource(newComment), HttpStatus.OK)
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