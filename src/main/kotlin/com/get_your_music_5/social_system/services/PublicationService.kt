package com.get_your_music_5.social_system.services

import com.get_your_music_5.social_system.models.Publication
import com.get_your_music_5.social_system.repositories.PublicationRepository
import com.get_your_music_5.users_system.models.Musician
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PublicationService(
        private val publicationRepository: PublicationRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)

    fun getAll(): List<Publication> = publicationRepository.findAll()

    fun getAllByMusicianId(musicianId: Long): List<Publication> = publicationRepository.getByMusicianId(musicianId)

    fun getById(publicationId: Long): Publication? = publicationRepository.findById(publicationId).orElse(null)

    @Transactional
    fun save(publication: Publication, musician: Musician): Publication {
        publication.publishDate = currentDateFormat
        publication.musician = musician
        return publicationRepository.save(publication)
    }

    @Transactional
    fun update(existed: Publication, publication: Publication): Publication {
        existed.videoUrl = publication.videoUrl
        existed.content = publication.content
        return publicationRepository.save(existed)
    }
}