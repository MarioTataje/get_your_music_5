package com.get_your_music_5.users_system.services

import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.models.Organizer
import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.repositories.ProfileRepository
import com.get_your_music_5.users_system.repositories.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ProfileService(
        private val profileRepository: ProfileRepository,
        private val userRepository: UserRepository,
        private val districtRepository: DistrictRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)



    fun getAll(): List<Profile> = profileRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Profile> = profileRepository.getByDistrictId(districtId)

    fun getById(profileId: Long): Profile {
        return profileRepository.findById(profileId)
                .orElseThrow { throw IllegalArgumentException("Profile not found $profileId") }
    }

    @Transactional
    fun save(profile: Profile, userId: Long, districtId: Long): Profile {
        val newProfile: Profile = if (profile.type == "Musician") Musician() else Organizer()
        newProfile.firstName = profile.firstName
        newProfile.lastName = profile.lastName
        newProfile.birthDate = profile.birthDate
        newProfile.phone = profile.phone
        newProfile.description = profile.description
        newProfile.registerDate = currentDateFormat
        newProfile.photoUrl = profile.photoUrl
        newProfile.type = if (profile.type == "Musician") "musician" else "organizer"
        newProfile.user = userRepository.findById(userId)
                .orElseThrow { throw IllegalArgumentException("User not found $userId") }
        newProfile.district = districtRepository.findById(districtId)
                .orElseThrow { throw IllegalArgumentException("District not found $districtId") }
        return profileRepository.save(newProfile)
    }

    @Transactional
    fun update(profileId: Long, profile: Profile): Profile {
        val existed = profileRepository.findById(profileId).
        orElseThrow { throw IllegalArgumentException("Profile not found $profileId") }
        existed.firstName = profile.firstName
        existed.lastName = profile.lastName
        existed.birthDate = profile.birthDate
        existed.phone = profile.phone
        existed.description = profile.description
        existed.photoUrl = profile.photoUrl
        return profileRepository.save(existed)
    }
}