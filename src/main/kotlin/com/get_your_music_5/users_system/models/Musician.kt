package com.get_your_music_5.users_system.models

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "musicians")
@PrimaryKeyJoinColumn(name = "musician_id")
class Musician(
        @Column(name = "rating", nullable = false)
        var rating: Double = 0.0): Profile()