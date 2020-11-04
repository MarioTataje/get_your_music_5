package com.get_your_music_5.social_system.services

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.repositories.PublicationRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
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

    fun getAllByMusicianId(musicianId: Long): List<Publication> = publicationRepository.getByMusicianId(musicianId)

    fun getById(publicationId: Long): Publication {
        return publicationRepository.findById(publicationId)
                .orElseThrow { throw IllegalArgumentException("Publication not found $publicationRepository") }
    }

    @Transactional
    fun save(publication: Publication, musicianId: Long): Publication {
        publication.publishDate = currentDateFormat
        publication.musician = musicianRepository.findById(musicianId)
                .orElseThrow { throw IllegalArgumentException("Musician not found $musicianId") }
        return publicationRepository.save(publication)
    }

    @Transactional
    fun update(publicationId: Long, publication: Publication): Publication {
        val existed = publicationRepository.findById(publicationId)
                .orElseThrow { throw IllegalArgumentException("Publication not found $publicationId") }
        existed.videoUrl = publication.videoUrl
        existed.content = publication.content
        return publicationRepository.save(existed)
    }
}