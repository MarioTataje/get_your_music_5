package com.get_your_music_5.social_system.repositories

import com.get_your_music_5.social_system.models.Publication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PublicationRepository: JpaRepository<Publication, Long> {
    fun getByMusicianId(musicianId: Long): List<Publication>
}