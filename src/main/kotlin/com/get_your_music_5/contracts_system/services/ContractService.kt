package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.repositories.ContractRepository
import com.get_your_music_5.contracts_system.repositories.ContractStateRepository
import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.users_system.patterns.ObserverImpl
import com.get_your_music_5.users_system.repositories.MusicianRepository
import com.get_your_music_5.users_system.repositories.OrganizerRepository
import com.get_your_music_5.users_system.services.NotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContractService(
        private val contractRepository: ContractRepository,
        private val organizerRepository: OrganizerRepository,
        private val musicianRepository: MusicianRepository,
        private val contractStateRepository: ContractStateRepository,
        private val districtRepository: DistrictRepository,
        private val notificationRepository: NotificationRepository
) {
    fun getAllByOrganizerId(organizerId: Long): List<Contract> {
        organizerRepository.findById(organizerId)
                .orElseThrow { NotFoundException("Organizer", "id", organizerId) }
        return contractRepository.getByOrganizerId(organizerId)
    }

    fun getAllByMusicianId(musicianId: Long): List<Contract> {
        musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        return contractRepository.getByMusicianId(musicianId)
    }

    fun getById(id: Long): Contract = contractRepository.findById(id)
            .orElseThrow { NotFoundException("Contract", "id", id) }

    @Transactional
    fun save(contract: Contract, organizerId: Long, musicianId: Long,
             districtId: Long): Contract {
        contract.organizer = organizerRepository.findById(organizerId)
                .orElseThrow { NotFoundException("Organizer", "id", organizerId) }
        contract.musician = musicianRepository.findById(musicianId)
                .orElseThrow { NotFoundException("Musician", "id", musicianId) }
        contract.district = districtRepository.findById(districtId)
                .orElseThrow { NotFoundException("District", "id", districtId) }
        contract.state = contractStateRepository.findById(1)
                .orElseThrow { NotFoundException("ContractState", "id", 1) }
        contract.addObserver(ObserverImpl(notificationRepository))
        contract.notifyObservers()
        return contractRepository.save(contract)
    }

    @Transactional
    fun updateState(id: Long, stateId: Long): Contract {
        val existed = contractRepository.findById(id)
                .orElseThrow { NotFoundException("Contract", "id", id) }
        existed.state = contractStateRepository.findById(stateId)
                .orElseThrow { NotFoundException("Contract State", "id", stateId) }
        existed.addObserver(ObserverImpl(notificationRepository))
        existed.notifyObservers()
        return contractRepository.save(existed)
    }
}