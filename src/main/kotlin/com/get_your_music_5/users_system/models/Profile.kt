package com.get_your_music_5.users_system.models

import com.get_your_music_5.locations.models.District
import javax.persistence.*

@Entity
@Table(name = "profiles")
class Profile(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "first_name", nullable = false)
        var firstName: String= "",

        @Column(name = "last_name", nullable = false)
        var lastName: String= "",

        @Column(name = "birth_date", nullable = false)
        var birthDate: String = "",

        @Column(name = "phone", nullable = false)
        var phone: String = "",

        @Column(name = "description")
        var description: String = "",

        @Column(name = "register_date", nullable = false)
        var registerDate: String = "",

        @Column(name = "photo_url")
        var photoUrl: String = "",

        @Column(name = "type", nullable = false)
        var type: String = "",

        @OneToOne()
        @JoinColumn(name = "user_id")
        var user: User?= null,

        @ManyToOne()
        @JoinColumn(name = "district_id")
        var district: District?= null
)