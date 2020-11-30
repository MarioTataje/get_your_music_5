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
            val contractState = contractStateService.getAll()
            if (contractState.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(contractState), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
}

    fun toResourceList(entities: List<ContractState>) : List<ContractStateResource>{
        val resources = mutableListOf<ContractStateResource>()
        for(entity in entities){
            val resource = ContractStateResource(entity.id, entity.name)
            resources.add(resource)
        }
        return resources
    }
}