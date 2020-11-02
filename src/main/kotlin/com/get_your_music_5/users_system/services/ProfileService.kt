package com.get_your_music_5.users_system.services

import com.get_your_music_5.locations.repositories.DistrictRepository
import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.repositories.ProfileRepository
import com.get_your_music_5.users_system.repositories.UserRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.lang.IllegalArgumentException

@Service
class ProfileService(
        private val profileRepository: ProfileRepository,
        private val userRepository: UserRepository,
        private val districtRepository: DistrictRepository
) {
    fun getAll(): List<Profile> = profileRepository.findAll()

    fun getAllByDistrictId(districtId: Long): List<Profile> = profileRepository.getByDistrictId(districtId)

    fun getById(profileId: Long): Profile {
        return profileRepository.findById(profileId)
                .orElseThrow { throw IllegalArgumentException("Profile not found $profileId") }
    }

    @Transactional
    fun save(profile: Profile, userId: Long, districtId: Long): Profile {
        val currentDate = LocalDateTime.now()
        val currentDateFormat = currentDate.format(DateTimeFormatter.ISO_DATE)
        profile.registerDate = currentDateFormat
        profile.user = userRepository.findById(userId)
                .orElseThrow { throw IllegalArgumentException("User not found $userId") }
        profile.district = districtRepository.findById(districtId)
                .orElseThrow { throw IllegalArgumentException("District not found $districtId") }
        return profileRepository.save(profile)
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