package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.resources.QualificationResource
import com.get_your_music_5.contracts_system.resources.SaveQualificationResource
import com.get_your_music_5.contracts_system.services.QualificationService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class QualificationController(
        private val qualificationService: QualificationService
) {

    @GetMapping("musicians/{musicianId}/qualifications")
    fun getAllQualificationsByMusicianId(@PathVariable musicianId: Long): List<QualificationResource>{
        val qualifications = qualificationService.getAllByMusicianId(musicianId)
        return toResourceList(qualifications)
    }

    @PostMapping("contracts/{contractId}/qualifications")
    fun create(@RequestBody qualification: SaveQualificationResource, @PathVariable contractId: Long)
            : QualificationResource {
        val newQualification = qualificationService.save(toEntity(qualification), contractId)
        return toResource(newQualification)
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