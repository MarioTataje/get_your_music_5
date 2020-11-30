package com.get_your_music_5.users_system.services

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.models.Organizer
import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.models.User
import com.get_your_music_5.users_system.repositories.ProfileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class ProfileService(
        private val profileRepository: ProfileRepository
) {
    private final val currentDate: LocalDateTime = LocalDateTime.now()
    val currentDateFormat: String = currentDate.format(DateTimeFormatter.ISO_DATE)

    fun getAll(): List<Profile> = profileRepository.findAll()

    fun getById(profileId: Long): Profile? {
        return profileRepository.findById(profileId).orElse(null)
    }

    @Transactional
    fun save(profile: Profile, user: User, district: District): Profile {
        val newProfile: Profile = if (profile.type == "Musician") Musician() else Organizer()
        newProfile.firstName = profile.firstName
        newProfile.lastName = profile.lastName
        newProfile.birthDate = profile.birthDate
        newProfile.phone = profile.phone
        newProfile.description = profile.description
        newProfile.registerDate = currentDateFormat
        newProfile.photoUrl = profile.photoUrl
        newProfile.type = if (profile.type == "Musician") "musician" else "organizer"
        newProfile.user = user
        newProfile.district = district
        return profileRepository.save(newProfile)
    }

    @Transactional
    fun update(existed: Profile, profile: Profile): Profile {
        existed.firstName = profile.firstName
        existed.lastName = profile.lastName
        existed.birthDate = profile.birthDate
        existed.phone = profile.phone
        existed.description = profile.description
        existed.photoUrl = profile.photoUrl
        return profileRepository.save(existed)
    }
}