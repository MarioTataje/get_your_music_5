package com.get_your_music_5.media_system.models

import com.get_your_music_5.users_system.models.Musician
import javax.persistence.*

@Entity
@Table(name = "genres")
class Genre(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "name", nullable = false)
        var name: String = "",

        @ManyToMany(mappedBy = "genres")
        var musicians: MutableList<Musician> = mutableListOf()
)