package com.get_your_music_5.users_system.models
import javax.persistence.*

@Entity
@Table(name = "users")
class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "email", nullable = false)
        var email: String= "",

        @Column(name = "password", nullable = false)
        var password: String= ""
)
