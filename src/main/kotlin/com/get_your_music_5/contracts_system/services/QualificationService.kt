package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.repositories.ContractRepository
import com.get_your_music_5.contracts_system.repositories.QualificationRepository
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QualificationService(
        private val qualificationRepository: QualificationRepository,
        private val contractRepository: ContractRepository,
        private val musicianRepository: MusicianRepository
) {

    fun getAllByMusicianId(musicianId:Long): List<Qualification> {
        musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return qualificationRepository.getByMusicianId(musicianId)
    }

    @Transactional
    fun save(contractId: Long, qualification: Qualification): Qualification{
        qualification.contract = contractRepository.findById(contractId)
                .orElseThrow { NotFoundException("Contract", "id", contractId) }
        return qualificationRepository.save(qualification)
    }
}