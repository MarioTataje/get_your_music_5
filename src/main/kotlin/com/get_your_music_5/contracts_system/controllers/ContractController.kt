package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.contracts_system.resources.ContractResource
import com.get_your_music_5.contracts_system.resources.SaveContractResource
import com.get_your_music_5.contracts_system.services.ContractService
import com.get_your_music_5.contracts_system.services.ContractStateService
import com.get_your_music_5.locations.services.DistrictService
import com.get_your_music_5.users_system.services.MusicianService
import com.get_your_music_5.users_system.services.OrganizerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class ContractController(
        private val contractService: ContractService,
        private val musicianService: MusicianService,
        private val organizerService: OrganizerService,
        private val contractStateService: ContractStateService,
        private val districtService: DistrictService
) {

    @GetMapping("/organizers/{organizerId}/contracts/")
    fun getAllContractsByOrganizerId(@PathVariable organizerId: Long): ResponseEntity<List<ContractResource>> {
        return try{
            organizerService.getById(organizerId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val contracts = contractService.getAllByOrganizerId(organizerId)
            if (contracts.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(contracts.map { contract -> this.toResource(contract) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/musicians/{musicianId}/contracts/")
    fun getAllContractsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<ContractResource>> {
        return try{
            musicianService.getById(musicianId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val contracts = contractService.getAllByMusicianId(musicianId)
            if (contracts.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(contracts.map { contract -> this.toResource(contract) }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/contracts/{contractId}/")
    fun getContractById(@PathVariable contractId: Long): ResponseEntity<ContractResource> {
        return try{
            val existed = contractService.getById(contractId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/organizers/{organizerId}/musicians/{musicianId}/districts/{districtId}/contracts/")
    fun create(@RequestBody contract: SaveContractResource, @PathVariable organizerId: Long,
               @PathVariable musicianId: Long, @PathVariable districtId: Long) : ResponseEntity<ContractResource> {
        return try{
            val state = contractStateService.getById(1) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val organizer = organizerService.getById(organizerId) ?:return ResponseEntity(HttpStatus.NOT_FOUND)
            val musician = musicianService.getById(musicianId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val district = districtService.getById(districtId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newContract = contractService.save(toEntity(contract), organizer, musician, district, state)
            ResponseEntity(toResource(newContract), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/contracts/{contractId}/states/{stateId}/")
    fun updateState(@PathVariable contractId: Long, @PathVariable stateId: Long): ResponseEntity<ContractResource>{
        return try{
            val state = contractStateService.getById(stateId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val contract = contractService.getById(contractId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val existed = contractService.updateState(contract, state)
            ResponseEntity(toResource(existed), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
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