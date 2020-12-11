package com.get_your_music_5.social_system.services

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.repositories.PublicationRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PublicationService(
        private val publicationRepository: PublicationRepository,
        private val musicianRepository: MusicianRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)

    fun getAll(): List<Publication> = publicationRepository.findAll()

    fun getAllByMusicianId(musicianId: Long): List<Publication> {
        musicianRepository.findById(musicianId).orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return publicationRepository.getByMusicianId(musicianId)
    }

    fun getById(id: Long): Publication = publicationRepository.findById(id)
            .orElseThrow { NotFoundException("Publication", "id", id) }

    @Transactional
    fun save(publication: Publication, musicianId: Long): Publication {
        publication.publishDate = currentDateFormat
        publication.musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return publicationRepository.save(publication)
    }

    @Transactional
    fun update(id: Long, publication: Publication): Publication {
        val existed = publicationRepository.findById(id)
                .orElseThrow { NotFoundException("Publication", "id", id) }
        existed.videoUrl = publication.videoUrl
        existed.content = publication.content
        return publicationRepository.save(existed)
    }
}