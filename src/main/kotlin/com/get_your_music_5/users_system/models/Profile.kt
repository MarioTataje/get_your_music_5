package com.get_your_music_5.users_system.models

import com.get_your_music_5.locations.models.District
import javax.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "profiles")
open class Profile(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        open var id: Long=0,

        @Column(name = "first_name", nullable = false)
        open var firstName: String = "",

        @Column(name = "last_name", nullable = false)
        open var lastName: String= "",

        @Column(name = "birth_date", nullable = false)
        open var birthDate: String = "",

        @Column(name = "phone", nullable = false)
        open var phone: String = "",

        @Column(name = "description")
        open var description: String = "",

        @Column(name = "register_date", nullable = false)
        open var registerDate: String = "",

        @Column(name = "photo_url")
        open var photoUrl: String = "",

        @Column(name = "type", nullable = false)
        open var type: String = "",

        @OneToOne
        @JoinColumn(name = "user_id")
        open var user: User?= null,

        @ManyToOne
        @JoinColumn(name = "district_id")
        open var district: District?= null)