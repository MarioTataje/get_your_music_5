package com.get_your_music_5.contracts_system.models

import javax.persistence.*

@Entity
@Table(name = "qualifications")
class Qualification(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "text", nullable = false)
        var text: String = "",

        @Column(name = "score", nullable = false)
        var score: Int = 0,

        @OneToOne
        @JoinColumn(name = "contract_id", referencedColumnName = "id")
        var contract: Contract?= null
)