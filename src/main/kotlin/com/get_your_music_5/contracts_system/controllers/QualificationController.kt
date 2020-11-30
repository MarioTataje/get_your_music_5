package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.resources.QualificationResource
import com.get_your_music_5.contracts_system.resources.SaveQualificationResource
import com.get_your_music_5.contracts_system.services.ContractService
import com.get_your_music_5.contracts_system.services.QualificationService
import com.get_your_music_5.users_system.services.MusicianService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class QualificationController(
        private val qualificationService: QualificationService,
        private val contractService: ContractService,
        private val musicianService: MusicianService
) {

    @GetMapping("musicians/{musicianId}/qualifications")
    fun getAllQualificationsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<QualificationResource>>{
        return try{
            musicianService.getById(musicianId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val qualifications = qualificationService.getAllByMusicianId(musicianId)
            if (qualifications.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(toResourceList(qualifications), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("contracts/{contractId}/qualifications")
    fun create(@RequestBody qualification: SaveQualificationResource, @PathVariable contractId: Long)
            : ResponseEntity<QualificationResource> {
        return try{
            val contract = contractService.getById(contractId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newQualification = qualificationService.save(toEntity(qualification), contract)
            ResponseEntity(toResource(newQualification), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toEntity(resource: SaveQualificationResource) = Qualification(
            text = resource.text,
            score = resource.score
    )

    fun toResourceList(entities: List<Qualification>) : List<QualificationResource>{
        val resources = mutableListOf<QualificationResource>()
        for(entity in entities){
            val resource = QualificationResource(
                    id = entity.id,
                    text = entity.text,
                    score = entity.score,
                    organizerName = entity.contract?.organizer?.firstName
            )
            resources.add(resource)
        }
        return resources
    }

    fun toResource(entity: Qualification) = QualificationResource(
            id = entity.id,
            text = entity.text,
            score = entity.score,
            organizerName = entity.contract?.organizer?.firstName
    )
}