package com.get_your_music_5.social_system.services

import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.repositories.CommentRepository
import com.get_your_music_5.social_system.repositories.PublicationRepository
import com.get_your_music_5.users_system.repositories.ProfileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
        private val commentRepository: CommentRepository,
        private val publicationRepository: PublicationRepository,
        private val profileRepository: ProfileRepository
){
    fun getAllByPublicationId(publicationId: Long): List<Comment> = commentRepository.getByPublicationId(publicationId)

    @Transactional
    fun save(comment: Comment, publicationId: Long, commenterId: Long): Comment {
        comment.publication = publicationRepository.findById(publicationId)
                .orElseThrow { throw IllegalArgumentException("Publication not found $publicationId") }
        comment.commenter = profileRepository.findById(commenterId)
                .orElseThrow{ throw IllegalArgumentException("Commenter not found $commenterId")}
        return commentRepository.save(comment)
    }

}