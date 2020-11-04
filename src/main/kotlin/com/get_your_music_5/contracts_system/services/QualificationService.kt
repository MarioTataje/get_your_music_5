package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.repositories.ContractRepository
import com.get_your_music_5.contracts_system.repositories.QualificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class QualificationService(
        private val qualificationRepository: QualificationRepository,
        private val contractRepository: ContractRepository
) {

    fun getAllByMusicianId(musicianId:Long): List<Qualification> = qualificationRepository.getByMusicianId(musicianId)

    @Transactional
    fun save(qualification: Qualification, contractId: Long): Qualification{
        qualification.contract = contractRepository.findById(contractId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Contract not found $contractId")}
        return qualificationRepository.save(qualification)
    }
}