package com.get_your_music_5.social_system.models

import com.get_your_music_5.users_system.models.Profile
import javax.persistence.*

@Entity
@Table(name = "messages")
class Message(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "text", nullable = false)
        var text: String = "",

        @Column(name = "send_date", nullable = false)
        var sendDate: String = "",

        @ManyToOne
        @JoinColumn(name = "sender_id", referencedColumnName = "id")
        var sender: Profile? = null,

        @ManyToOne
        @JoinColumn(name = "receiver_id", referencedColumnName = "id")
        var receiver: Profile? = null)