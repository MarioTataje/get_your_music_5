package com.get_your_music_5.social_system.services

import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.repositories.CommentRepository
import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.patterns.ObserverImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
        private val commentRepository: CommentRepository,
        private val notificationRepository: NotificationRepository
){
    fun getAllByPublicationId(publicationId: Long): List<Comment> = commentRepository.getByPublicationId(publicationId)

    @Transactional
    fun save(comment: Comment, publication: Publication, commenter: Profile): Comment {
        comment.publication = publication
        comment.commenter = commenter
        comment.addObserver(ObserverImpl(notificationRepository))
        comment.notifyObservers()
        return commentRepository.save(comment)
    }

}