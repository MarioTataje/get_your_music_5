package com.get_your_music_5.contracts_system.repositories

import com.get_your_music_5.contracts_system.models.Qualification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface QualificationRepository : JpaRepository<Qualification, Long> {

    @Query("SELECT q FROM Qualification q join Contract c on q.contract.id=c.id" +
            " join Musician m on c.musician.id=m.id where m.id=:musicianId")
    fun getByMusicianId(@Param("musicianId") musicianId:Long): List<Qualification>
}