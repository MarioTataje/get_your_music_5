package com.get_your_music_5.contracts_system.repositories

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.locations.models.District
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
interface ContractRepository : JpaRepository<Contract, Long> {
    fun getByOrganizerId(organizerId: Long): List<Contract>
    fun getByMusicianId(musicianId: Long): List<Contract>
}