package com.get_your_music_5.users_system.services

import com.get_your_music_5.users_system.models.Organizer
import com.get_your_music_5.users_system.repositories.OrganizerRepository
import org.springframework.stereotype.Service

@Service
class OrganizerService(
        private val organizerRepository: OrganizerRepository
) {
    fun getAll(): List<Organizer> = organizerRepository.findAll()

    fun getById(id: Long): Organizer = organizerRepository
            .findById(id).orElseThrow { NotFoundException("Profile", "id", id) }

}