package com.get_your_music_5.contracts_system.controllers

import com.get_your_music_5.contracts_system.models.Qualification
import com.get_your_music_5.contracts_system.resources.QualificationResource
import com.get_your_music_5.contracts_system.resources.SaveQualificationResource
import com.get_your_music_5.contracts_system.services.QualificationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/")
class QualificationController(
        private val qualificationService: QualificationService
) {

    @GetMapping("musicians/{musicianId}/qualifications/")
    fun getAllQualificationsByMusicianId(@PathVariable musicianId: Long): ResponseEntity<List<QualificationResource>>{
        val qualifications = qualificationService.getAllByMusicianId(musicianId)
        if (qualifications.isEmpty()) return ResponseEntity(HttpStatus.NO_CONTENT)
        return ResponseEntity(qualifications.map { qualification -> this.toResource(qualification) }, HttpStatus.OK)
    }

    @PostMapping("contracts/{contractId}/qualifications/")
    fun create(@RequestBody qualification: SaveQualificationResource, @PathVariable contractId: Long)
            : ResponseEntity<QualificationResource> {
        val newQualification = qualificationService.save(contractId, toEntity(qualification))
        return ResponseEntity(toResource(newQualification), HttpStatus.OK)
    }

    fun toEntity(resource: SaveQualificationResource) = Qualification(
            text = resource.text,
            score = resource.score
    )

    fun toResource(entity: Qualification) = QualificationResource(
            id = entity.id,
            text = entity.text,
            score = entity.score,
            organizerName = entity.contract?.organizer?.firstName
    )
}