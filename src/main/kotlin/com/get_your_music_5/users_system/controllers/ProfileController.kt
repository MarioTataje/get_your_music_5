package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.locations.services.DistrictService
import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.resources.ProfileResource
import com.get_your_music_5.users_system.resources.SaveProfileResource
import com.get_your_music_5.users_system.services.ProfileService
import com.get_your_music_5.users_system.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class ProfileController(
        private val profileService: ProfileService,
        private val userService: UserService,
        private val districtService: DistrictService
) {

    @GetMapping("/profiles/")
    fun getAllProfiles(): ResponseEntity<List<ProfileResource>> {
        return try{
            val profiles = profileService.getAll()
            if (profiles.isEmpty())
                return ResponseEntity(HttpStatus.NO_CONTENT)
            ResponseEntity(profiles.map{profile -> this.toResource(profile)  }, HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @GetMapping("/profiles/{profileId}/")
    fun getProfileById(@PathVariable profileId: Long): ResponseEntity<ProfileResource> {
        return try{
            val existed = profileService.getById(profileId)
            return if (existed != null) ResponseEntity(toResource(existed), HttpStatus.OK)
            else ResponseEntity(HttpStatus.NOT_FOUND)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PostMapping("/districts/{districtId}/users/{userId}/profiles/")
    fun create(@RequestBody profile: SaveProfileResource, @PathVariable userId: Long,
               @PathVariable districtId: Long) : ResponseEntity<ProfileResource> {
        return try{
            val user = userService.getById(userId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val district = districtService.getById(districtId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val newProfile = profileService.save(toEntity(profile), user, district)
            ResponseEntity(toResource(newProfile), HttpStatus.CREATED)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    @PutMapping("/profiles/{profileId}/")
    fun update(@PathVariable profileId: Long, @RequestBody profile: SaveProfileResource):
            ResponseEntity<ProfileResource> {
        return try{
            val existed = profileService.getById(profileId) ?: return ResponseEntity(HttpStatus.NOT_FOUND)
            val updated = profileService.update(existed, toEntity(profile))
            ResponseEntity(toResource(updated), HttpStatus.OK)
        } catch (e: Exception){
            ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }
    }

    fun toEntity(resource: SaveProfileResource) = Profile(
            firstName = resource.firstName,
            lastName = resource.lastName,
            birthDate = resource.birthDate,
            phone = resource.phone,
            description = resource.description,
            photoUrl = resource.photoUrl,
            type = resource.type
    )

    fun toResource(entity: Profile) = ProfileResource(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            birthDate = entity.birthDate,
            phone = entity.phone,
            description = entity.description,
            registerDate = entity.registerDate,
            photoUrl = entity.photoUrl,
            type = entity.type,
            userEmail = entity.user?.email,
            districtName = entity.district?.name
    )
}