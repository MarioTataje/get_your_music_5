package com.get_your_music_5.social_system.services

import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.social_system.models.Message
import com.get_your_music_5.social_system.repositories.MessageRepository
import com.get_your_music_5.users_system.patterns.ObserverImpl
import com.get_your_music_5.users_system.repositories.ProfileRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class MessageService(
        private val messageRepository: MessageRepository,
        private val profileRepository: ProfileRepository,
        private val notificationRepository: NotificationRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)

    fun getAllBySenderId(senderId: Long): List<Message> {
        profileRepository.findById(senderId)
                .orElseThrow { NotFoundException("Profile", "id", senderId) }
        return messageRepository.getBySenderId(senderId)
    }

    fun getAllByReceiverId(receiverId: Long): List<Message> {
        profileRepository.findById(receiverId).orElseThrow { NotFoundException("Profile", "id", receiverId) }
        return messageRepository.getByReceiverId(receiverId)
    }

    fun getById(id: Long): Message = messageRepository.findById(id)
            .orElseThrow { NotFoundException("Message", "id", id) }

    @Transactional
    fun save(message: Message, senderId: Long, receiverId: Long): Message {
        message.sendDate = currentDateFormat
        message.sender = profileRepository.findById(senderId)
                .orElseThrow { NotFoundException("Profile", "id", senderId) }
        message.receiver = profileRepository.findById(receiverId)
                .orElseThrow { NotFoundException("Profile", "id", receiverId) }
        message.addObserver(ObserverImpl(notificationRepository))
        message.notifyObservers()
        return messageRepository.save(message)
    }
}