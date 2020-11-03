package com.get_your_music_5.contracts_system.models

import javax.persistence.*

@Entity
@Table(name = "contract_states")
class ContractState (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "name", nullable = false)
        var name: String= "")