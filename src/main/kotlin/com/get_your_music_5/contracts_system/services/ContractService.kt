package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.repositories.ContractRepository
import com.get_your_music_5.contracts_system.repositories.ContractStateRepository
import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.users_system.patterns.ObserverImpl
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.repositories.OrganizerRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContractService(
        private val contractRepository: ContractRepository,
        private val organizerRepository: OrganizerRepository,
        private val musicianRepository: MusicianRepository,
        private val districtRepository: DistrictRepository,
        private val contractStateRepository: ContractStateRepository,
        private val notificationRepository: NotificationRepository
) {
    fun getAllByOrganizerId(organizerId: Long): List<Contract> = contractRepository.getByOrganizerId(organizerId)

    fun getAllByMusicianId(musicianId: Long): List<Contract> = contractRepository.getByMusicianId(musicianId)

    fun getById(contractId: Long): Contract {
        return contractRepository.findById(contractId)
                .orElseThrow { throw IllegalArgumentException("Contract not found $contractId") }
    }

    @Transactional
    fun save(contract: Contract, organizerId: Long, musicianId: Long, districtId: Long): Contract {
        contract.organizer = organizerRepository.findById(organizerId)
                .orElseThrow { throw IllegalArgumentException("Organizer not found $organizerId") }
        contract.musician = musicianRepository.findById(musicianId)
                .orElseThrow { throw IllegalArgumentException("Musician not found $musicianId") }
        contract.district = districtRepository.findById(districtId)
                .orElseThrow { throw java.lang.IllegalArgumentException("District not found $districtId")}
        contract.state = contractStateRepository.findById(1)
                .orElse(null)
        contract.addObserver(ObserverImpl(notificationRepository))
        contract.notifyObservers()
        return contractRepository.save(contract)
    }

    @Transactional
    fun updateState(contractId: Long, stateId: Long): Contract {
        val existed = contractRepository.findById(contractId)
                .orElseThrow { throw java.lang.IllegalArgumentException("Contract not found $contractId")}
        existed.state = contractStateRepository.findById(stateId)
                .orElseThrow { throw java.lang.IllegalArgumentException("State not found $stateId")}
        existed.addObserver(ObserverImpl(notificationRepository))
        existed.notifyObservers()
        return contractRepository.save(existed)
    }
}