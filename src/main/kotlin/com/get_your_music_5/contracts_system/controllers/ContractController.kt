package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.resources.ContractResource
import com.get_your_music_5.contracts_system.resources.SaveContractResource
import com.get_your_music_5.contracts_system.services.ContractService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class ContractController(
        private val contractService: ContractService
) {

    @GetMapping("/organizers/{organizerId}/contracts")
    fun getAllContractsByOrganizerId(@PathVariable organizerId: Long): List<ContractResource> {
        val contracts = contractService.getAllByOrganizerId(organizerId)
        return toResourceList(contracts)
    }

    @GetMapping("/musicians/{musicianId}/contracts")
    fun getAllContractsByMusicianId(@PathVariable musicianId: Long): List<ContractResource> {
        val contracts = contractService.getAllByMusicianId(musicianId)
        return toResourceList(contracts)
    }

    @GetMapping("/contracts/{contractId}")
    fun getContractById(@PathVariable contractId: Long): ContractResource {
        val existed = contractService.getById(contractId)
        return toResource(existed)
    }

    @PostMapping("/organizers/{organizerId}/musicians/{musicianId}/districts/{districtId}/contracts")
    fun create(@RequestBody contract: SaveContractResource, @PathVariable organizerId: Long,
               @PathVariable musicianId: Long, @PathVariable districtId: Long) : ContractResource {
        val newContract = contractService.save(toEntity(contract), organizerId, musicianId, districtId)
        return toResource(newContract)
    }

    @PutMapping("/contracts/{contractId}/states/{stateId}")
    fun updateState(@PathVariable contractId: Long, @PathVariable stateId: Long): ContractResource{
        val existed = contractService.updateState(contractId, stateId)
        return toResource(existed)
    }

    fun toEntity(resource: SaveContractResource) = Contract(
            name = resource.name,
            address = resource.address,
            reference = resource.reference,
            startDate = resource.startDate,
            endDate = resource.endDate
    )

    fun toResourceList(entities: List<Contract>) : List<ContractResource>{
        val resources = mutableListOf<ContractResource>()
        for(entity in entities){
            val resource = ContractResource(
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
            resources.add(resource)
        }
        return resources
    }

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