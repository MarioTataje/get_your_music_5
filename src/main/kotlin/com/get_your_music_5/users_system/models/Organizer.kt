package com.get_your_music_5.users_system.models

import com.get_your_music_5.contracts_system.models.Contract
import java.io.Serializable
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.PrimaryKeyJoinColumn
import javax.persistence.Table

@Entity
@Table(name = "organizers")
class Organizer(
        @OneToMany(mappedBy = "organizer")
        val contracts: List<Contract> = emptyList()
): Profile(), Serializable