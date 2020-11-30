package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.repositories.QualificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QualificationService(
        private val qualificationRepository: QualificationRepository
) {

    fun getAllByMusicianId(musicianId:Long): List<Qualification> = qualificationRepository.getByMusicianId(musicianId)

    @Transactional
    fun save(qualification: Qualification, contract: Contract): Qualification{
        qualification.contract = contract
        return qualificationRepository.save(qualification)
    }
}