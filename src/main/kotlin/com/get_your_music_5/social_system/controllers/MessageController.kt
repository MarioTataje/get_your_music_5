package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Message
import com.get_your_music_5.social_system.resources.*
import com.get_your_music_5.social_system.services.MessageService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class MessageController(
        private val messageService: MessageService
) {

    @GetMapping("/senders/{senderId}/messages")
    fun getAllMessagesSenderById(@PathVariable senderId: Long): ResponseEntity<List<MessageResource>>{
        val messages = messageService.getAllBySenderId(senderId)
        if (messages.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(messages.map { message -> this.toResource(message) }, HttpStatus.OK)
    }

    @GetMapping("/receivers/{receiverId}/messages")
    fun getAllMessagesReceiverById(@PathVariable receiverId: Long): ResponseEntity<List<MessageResource>>{
        val messages = messageService.getAllByReceiverId(receiverId)
        if (messages.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(messages.map { message -> this.toResource(message) }, HttpStatus.OK)
    }

    @GetMapping("/messages/{id}/")
    fun getMessageById(@PathVariable id: Long): ResponseEntity<MessageResource> {
        val existed = messageService.getById(id)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    @PostMapping("/senders/{senderId}/receivers/{receiverId}/messages")
    fun create(@RequestBody message: SaveMessageResource, @PathVariable senderId: Long,
               @PathVariable receiverId: Long): ResponseEntity<MessageResource>{
        val newMessage = messageService.save(toEntity(message), senderId, receiverId)
        return ResponseEntity(toResource(newMessage), HttpStatus.OK)
    }

    fun toEntity(resource: SaveMessageResource) = Message(
            text = resource.text
    )

    fun toResource(entity: Message) = MessageResource(
            id = entity.id,
            text = entity.text,
            sendDate = entity.sendDate,
            senderName = entity.sender?.firstName,
            receiverName = entity.receiver?.firstName
    )
}