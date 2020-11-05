package com.get_your_music_5.users_system.models

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.media_system.models.Genre
import com.get_your_music_5.media_system.models.Instrument
import com.get_your_music_5.social_system.models.Following
import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "musicians")
class Musician(
        @Column(name = "rating", nullable = false)
        var rating: Double = 0.0,

        @ManyToMany
        @JoinTable(name = "musicians_genres",
                joinColumns = [JoinColumn(name = "musician_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "genre_id", referencedColumnName = "id")])
        var genres: MutableList<Genre> = mutableListOf(),

        @ManyToMany
        @JoinTable(name = "musicians_instruments",
                joinColumns = [JoinColumn(name = "musician_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "instrument_id", referencedColumnName = "id")])
        var instruments: MutableList<Instrument> = mutableListOf(),

        @OneToMany(mappedBy = "musician")
        val contracts: List<Contract> = emptyList()
): Profile(), Serializable