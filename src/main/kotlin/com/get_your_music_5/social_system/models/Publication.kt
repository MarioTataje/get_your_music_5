package com.get_your_music_5.social_system.models

import com.get_your_music_5.users_system.models.Musician
import javax.persistence.*

@Entity
@Table(name = "publications")
class Publication(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "video_url", nullable = false)
        var videoUrl: String = "",

        @Column(name = "content", nullable = false)
        var content: String = "",

        @Column(name = "publish_date")
        var publishDate: String = "",

        @ManyToOne
        @JoinColumn(name = "musician_id")
        var musician: Musician? = null
)