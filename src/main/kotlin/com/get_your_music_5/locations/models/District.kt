package com.get_your_music_5.locations.models

import javax.persistence.*

@Entity
@Table(name = "districts")
class District(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "name", nullable = false)
        var name: String= "",

        @ManyToOne()
        @JoinColumn(name = "province_id")
        var province: Province?= null
)