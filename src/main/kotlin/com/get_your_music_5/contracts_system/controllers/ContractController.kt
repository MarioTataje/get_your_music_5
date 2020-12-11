package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.resources.ContractResource
import com.get_your_music_5.contracts_system.resources.SaveContractResource
import com.get_your_music_5.contracts_system.services.ContractService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class ContractController(
        private val contractService: ContractService
) {

    @GetMapping("/organizers/{organizerId}/contracts/")
    fun getAllContractsByOrganizerId(@PathVariable organizerId: Long): ResponseEntity<List<ContractResource>> {
        val contracts = contractService.getAllByOrganizerId(organizerId)
        if (contracts.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(contracts.map { contract -> this.toResource(contract) }, HttpStatus.OK)
    }

    @GetMapping("/musicians/{musicianId}/contracts/")
    fun getAllContractsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<ContractResource>> {
        val contracts = contractService.getAllByMusicianId(musicianId)
        if (contracts.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(contracts.map { contract -> this.toResource(contract) }, HttpStatus.OK)
    }

    @GetMapping("/contracts/{contractId}/")
    fun getContractById(@PathVariable contractId: Long): ResponseEntity<ContractResource> {
        val existed = contractService.getById(contractId)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    @PostMapping("/organizers/{organizerId}/musicians/{musicianId}/districts/{districtId}/contracts/")
    fun create(@RequestBody contract: SaveContractResource, @PathVariable organizerId: Long,
               @PathVariable musicianId: Long, @PathVariable districtId: Long) : ResponseEntity<ContractResource> {
        val newContract = contractService.save(toEntity(contract), organizerId, musicianId, districtId)
        return ResponseEntity(toResource(newContract), HttpStatus.OK)
    }

    @PutMapping("/contracts/{id}/states/{stateId}/")
    fun updateState(@PathVariable id: Long, @PathVariable stateId: Long): ResponseEntity<ContractResource>{
        val existed = contractService.updateState(id, stateId)
        return ResponseEntity(toResource(existed), HttpStatus.OK)
    }

    fun toEntity(resource: SaveContractResource) = Contract(
            name = resource.name,
            address = resource.address,
            reference = resource.reference,
            startDate = resource.startDate,
            endDate = resource.endDate
    )

    fun toResource(entity: Contract) = ContractResource(
            id = entity.id,
            name = entity.name,
            address = entity.address,
            reference = entity.reference,
            startDate = entity.startDate,
            endDate = entity.endDate,
            organizerName = entity.organizer?.firstName,
            musicianName = entity.musician?.firstName,
            districtName = entity.district?.name,
            state = entity.state?.name
    )
}