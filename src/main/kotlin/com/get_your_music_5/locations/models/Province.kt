package com.get_your_music_5.locations.models

import javax.persistence.*

@Entity
@Table(name="provinces")
class Province(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "name", nullable = false)
        var name: String= "",

        @ManyToOne()
        @JoinColumn(name = "region_id")
        var region: Region?= null
)