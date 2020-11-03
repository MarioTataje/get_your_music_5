package com.get_your_music_5.contracts_system.repositories

import com.get_your_music_5.contracts_system.models.Qualification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface QualificationRepository : JpaRepository<Qualification, Long> {
}