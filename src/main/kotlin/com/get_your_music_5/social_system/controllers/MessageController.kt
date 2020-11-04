package com.get_your_music_5.social_system.controllers

import com.get_your_music_5.social_system.models.Message
import com.get_your_music_5.social_system.resources.*
import com.get_your_music_5.social_system.services.MessageService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class MessageController(
        private val messageService: MessageService
) {
    @GetMapping("/senders/{senderId}/messages")
    fun getAllMessagesSenderById(@PathVariable senderId: Long): List<MessageResource>{
        val messages = messageService.getAllBySenderId(senderId)
        return toResourceList(messages)
    }

    @GetMapping("/receivers/{receiverId}/messages")
    fun getAllMessagesReceiverById(@PathVariable receiverId: Long): List<MessageResource>{
        val messages = messageService.getAllByReceiverId(receiverId)
        return toResourceList(messages)
    }

    @GetMapping("/messages/{messageId}")
    fun getMessageById(@PathVariable messageId: Long): MessageResource {
        val existed = messageService.getById(messageId)
        return toResource(existed)
    }

    @PostMapping("/senders/{senderId}/receivers/{receiverId}/messages")
    fun create(@RequestBody message: SaveMessageResource, @PathVariable senderId: Long,
               @PathVariable receiverId: Long): MessageResource{
        val newMessage = messageService.save(toEntity(message), senderId, receiverId)
        return toResource(newMessage)
    }

    fun toEntity(resource: SaveMessageResource) = Message(
            text = resource.text
    )

    fun toResourceList(entities: List<Message>) : List<MessageResource>{
        val resources = mutableListOf<MessageResource>()
        for(entity in entities){
            val resource = MessageResource(
                    id = entity.id,
                    text = entity.text,
                    sendDate = entity.sendDate,
                    senderName = entity.sender?.firstName,
                    receiverName = entity.receiver?.firstName
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Message) = MessageResource(
            id = entity.id,
            text = entity.text,
            sendDate = entity.sendDate,
            senderName = entity.sender?.firstName,
            receiverName = entity.receiver?.firstName
    )
}