package com.get_your_music_5.social_system.repositories

import com.get_your_music_5.social_system.models.Publication
import org.springframework.data.jpa.repository.JpaRepository

interface PublicationRepository: JpaRepository<Publication, Long> {
}