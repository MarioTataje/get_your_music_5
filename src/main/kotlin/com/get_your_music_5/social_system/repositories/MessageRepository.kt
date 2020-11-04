package com.get_your_music_5.social_system.repositories

import com.get_your_music_5.social_system.models.Message
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository: JpaRepository<Message, Long> {
    fun getBySenderId(senderId: Long): List<Message>
    fun getByReceiverId(receiverId: Long): List<Message>
}