package com.get_your_music_5.social_system.repositories

import com.get_your_music_5.social_system.models.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    fun getByPublicationId(publicationId: Long): List<Comment>
}