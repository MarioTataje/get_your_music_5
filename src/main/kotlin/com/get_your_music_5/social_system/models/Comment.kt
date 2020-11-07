package com.get_your_music_5.social_system.models

import com.get_your_music_5.users_system.models.Profile
import com.get_your_music_5.users_system.patterns.Observer
import com.get_your_music_5.users_system.patterns.Subject
import javax.persistence.*

@Entity
@Table(name = "comments")
class Comment(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Column(name = "text", nullable = false)
        var text: String = "",

        @ManyToOne
        @JoinColumn(name = "profile_id", nullable = false)
        var commenter: Profile? = null,

        @ManyToOne
        @JoinColumn(name = "publication_id", nullable = false)
        var publication: Publication? = null
): Subject {
        @Transient
        private val observersList: ArrayList<Observer> = ArrayList()

        override fun addObserver(o: Observer) {
                observersList.add(o)
        }

        override fun notifyObservers() {
                for (observer in observersList) {
                        observer.update(this)
                }
        }
}