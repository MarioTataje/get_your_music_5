package com.get_your_music_5.users_system.models

import javax.persistence.Entity
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "organizers")
@PrimaryKeyJoinColumn(name = "organizer_id")
class Organizer(): Profile()