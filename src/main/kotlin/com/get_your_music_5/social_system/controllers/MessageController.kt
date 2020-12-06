package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Message
import com.get_your_music_5.social_system.resources.*
import com.get_your_music_5.social_system.services.MessageService
import com.get_your_music_5.users_system.services.ProfileService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class MessageController(
        private val messageService: MessageService,
        private val profileService: ProfileService
) {

    @GetMapping("/senders/{senderId}/messages")
    fun getAllMessagesSenderById(@PathVariable senderId: Long): ResponseEntity<List<MessageResource>>{
        return try{
            profileService.getById(senderId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val messages = messageService.getAllBySenderId(senderId)
            if (messages.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(messages.map { message -> this.toResource(message) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/receivers/{receiverId}/messages")
    fun getAllMessagesReceiverById(@PathVariable receiverId: Long): ResponseEntity<List<MessageResource>>{
        return try{
            profileService.getById(receiverId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val messages = messageService.getAllByReceiverId(receiverId)
            if (messages.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(messages.map { message -> this.toResource(message) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/messages/{messageId}/")
    fun getMessageById(@PathVariable messageId: Long): ResponseEntity<MessageResource> {
        return try{
            val existed = messageService.getById(messageId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/senders/{senderId}/receivers/{receiverId}/messages")
    fun create(@RequestBody message: SaveMessageResource, @PathVariable senderId: Long,
               @PathVariable receiverId: Long): ResponseEntity<MessageResource>{
        return try{
            val sender = profileService.getById(senderId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val receiver =  profileService.getById(receiverId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newMessage = messageService.save(toEntity(message), sender, receiver)
            ResponseEntity(toResource(newMessage), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
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