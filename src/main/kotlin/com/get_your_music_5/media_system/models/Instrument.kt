package com.get_your_music_5.media_system.models

import javax.persistence.*

@Entity
@Table(name = "instruments")
class Instrument(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "name", nullable = false)
        var name: String = ""
)