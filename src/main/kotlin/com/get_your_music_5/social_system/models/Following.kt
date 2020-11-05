package com.get_your_music_5.social_system.models

import com.get_your_music_5.users_system.models.Musician
import javax.persistence.*

@Entity
@Table(name = "followings")
class Following(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long=0,

        @ManyToOne
        @JoinColumn(name = "follower_id")
        var follower: Musician? = null,

        @ManyToOne
        @JoinColumn(name = "followed_id")
        var followed: Musician? = null,

        @Column(name = "follow_date", nullable = false)
        var followDate: String = ""
)