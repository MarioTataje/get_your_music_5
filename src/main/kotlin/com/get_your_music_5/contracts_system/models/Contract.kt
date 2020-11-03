package com.get_your_music_5.contracts_system.models

import com.get_your_music_5.locations.models.District
import com.get_your_music_5.users_system.models.Musician
import com.get_your_music_5.users_system.models.Organizer
import javax.persistence.*

@Entity
@Table(name = "contracts")
class Contract(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "name", nullable = false)
        var name: String = "",

        @Column(name = "address", nullable = false)
        var address: String = "",

        @Column(name = "reference", nullable = false)
        var reference: String = "",

        @Column(name = "start_date", nullable = false)
        var startDate: String = "",

        @Column(name = "end_date", nullable = false)
        var endDate: String = "",

        @ManyToOne
        @JoinColumn(name = "organizer_id")
        var organizer: Organizer? = null,

        @ManyToOne
        @JoinColumn(name = "musician_id")
        var musician: Musician? = null,

        @ManyToOne
        @JoinColumn(name = "district_id")
        var district: District? = null,

        @ManyToOne
        @JoinColumn(name = "state_id")
        var state: ContractState? = null,

        @OneToOne(mappedBy = "contract")
        var qualification: Qualification? = null

)