package com.get_your_music_5.media_system.models

import com.get_your_music_5.users_system.models.Profile
import javax.persistence.*


@Entity
@Table(name = "notifications")
class Notification(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @Column(name = "message", nullable = false)
        var message: String = "",

        @ManyToOne
        @JoinColumn(name = "profile_id")
        var profile: Profile?= null)