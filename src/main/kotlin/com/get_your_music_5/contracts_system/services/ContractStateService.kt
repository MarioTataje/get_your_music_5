package com.get_your_music_5.contracts_system.services

import com.get_your_music_5.contracts_system.models.ContractState
import com.get_your_music_5.contracts_system.repositories.ContractStateRepository
import org.springframework.stereotype.Service

@Service
class ContractStateService(
        private val contractStateRepository: ContractStateRepository
) {
    fun getAll(): List<ContractState> = contractStateRepository.findAll()

    fun getById(id: Long): ContractState? = contractStateRepository.findById(id).orElse(null)
}