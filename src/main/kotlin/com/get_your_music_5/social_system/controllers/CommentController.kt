package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.resources.CommentResource
import com.get_your_music_5.social_system.resources.SaveCommentResource
import com.get_your_music_5.social_system.services.CommentService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class CommentController(
        private val commentService: CommentService
){

    @GetMapping("/publications/{publicationId}/comments")
    fun getAllCommentsByPublicationId(@PathVariable publicationId: Long) : List<CommentResource> {
        val comments = commentService.getAllByPublicationId(publicationId)
        return toResourceList(comments)
    }

    @PostMapping("/publications/{publicationId}/commentators/{commenterId}/comments")
    fun create(@RequestBody comment: SaveCommentResource,
               @PathVariable publicationId: Long, @PathVariable commenterId: Long) : CommentResource {
        val newComment = commentService.save(toEntity(comment), publicationId, commenterId)
        return toResource(newComment)
    }

    fun toEntity(resource: SaveCommentResource) = Comment(
            text = resource.text
    )

    fun toResourceList(entities: List<Comment>) : List<CommentResource>{
        val resources = mutableListOf<CommentResource>()
        for(entity in entities){
            val resource = CommentResource(
                    id = entity.id,
                    text = entity.text,
                    commenterName = entity.commenter?.firstName,
                    publicationContent = entity.publication?.content
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Comment) = CommentResource(
            id = entity.id,
            text = entity.text,
            commenterName = entity.commenter?.firstName,
            publicationContent = entity.publication?.content
    )
}