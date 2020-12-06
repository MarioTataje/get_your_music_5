package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.ContractState
import com.get_your_music_5.contracts_system.resources.ContractStateResource
import com.get_your_music_5.contracts_system.services.ContractStateService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/")
class ContractStateController(
        private val contractStateService: ContractStateService
) {
    @GetMapping("/contractStates/")
    fun getAllContractStates(): ResponseEntity<List<ContractStateResource>> {
        return try{
            val contractStates = contractStateService.getAll()
            if (contractStates.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(contractStates.map { contractState -> this.toResource(contractState) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
}

    fun toResource(entity: ContractState) = ContractStateResource(
            id = entity.id,
            state = entity.name
    )
}