package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.models.ContractState
import com.get_your_music_5.contracts_system.repositories.ContractRepository
import com.get_your_music_5.locations.models.District
import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.models.Organizer
import com.get_your_music_5.users_system.patterns.ObserverImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContractService(
        private val contractRepository: ContractRepository,
        private val notificationRepository: NotificationRepository
) {
    fun getAllByOrganizerId(organizerId: Long): List<Contract> = contractRepository.getByOrganizerId(organizerId)

    fun getAllByMusicianId(musicianId: Long): List<Contract> = contractRepository.getByMusicianId(musicianId)

    fun getById(contractId: Long): Contract? = contractRepository.findById(contractId).orElse(null)

    @Transactional
    fun save(contract: Contract, organizer: Organizer,
             musician: Musician, district: District, state: ContractState): Contract {
        contract.organizer = organizer
        contract.musician = musician
        contract.district = district
        contract.state = state
        contract.addObserver(ObserverImpl(notificationRepository))
        contract.notifyObservers()
        return contractRepository.save(contract)
    }

    @Transactional
    fun updateState(contract: Contract, state: ContractState): Contract {
        contract.state = state
        contract.addObserver(ObserverImpl(notificationRepository))
        contract.notifyObservers()
        return contractRepository.save(contract)
    }
}