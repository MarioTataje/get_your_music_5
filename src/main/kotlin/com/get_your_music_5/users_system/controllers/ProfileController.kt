package com.get_your_music_5.users_system.controllers

import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.resources.ProfileResource
import com.get_your_music_5.users_system.resources.SaveProfileResource
import com.get_your_music_5.users_system.services.ProfileService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
class ProfileController(
        private val profileService: ProfileService
) {

    @GetMapping("/profiles")
    fun getAllProfiles(): List<ProfileResource> {
        val profiles = profileService.getAll()
        return toResourceList(profiles)
    }

    @GetMapping("/profiles/{profileId}")
    fun getProfileById(@PathVariable profileId: Long): ProfileResource {
        val existed = profileService.getById(profileId)
        return toResource(existed)
    }

    @PostMapping("/districts/{districtId}/users/{userId}/profiles")
    fun create(@RequestBody profile: SaveProfileResource, @PathVariable userId: Long,
               @PathVariable districtId: Long) : ProfileResource {
        val newProfile = profileService.save(toEntity(profile), userId, districtId)
        return toResource(newProfile)
    }

    @PutMapping("/profiles/{profileId}")
    fun update(@PathVariable profileId: Long, @RequestBody profile: SaveProfileResource): ProfileResource {
        val existed = profileService.update(profileId, toEntity(profile))
        return toResource(existed)
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

    fun toResourceList(entities: List<Profile>) : List<ProfileResource>{
        val resources = mutableListOf<ProfileResource>()
        for(entity in entities){
            val resource = ProfileResource(
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
            resources.add(resource)
        }
        return resources
    }

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