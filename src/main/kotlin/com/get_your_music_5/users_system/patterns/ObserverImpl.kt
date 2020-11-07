package com.get_your_music_5.users_system.patterns

import com.get_your_music_5.contracts_system.models.Contract
import com.get_your_music_5.media_system.models.Notification
import com.get_your_music_5.media_system.repositories.NotificationRepository
import com.get_your_music_5.social_system.models.Comment
import com.get_your_music_5.social_system.models.Following
import com.get_your_music_5.social_system.models.Message
import com.get_your_music_5.users_system.models.Profile

class ObserverImpl(
        private val notificationRepository: NotificationRepository
): Observer {
    override fun update(subject: Subject) {
        val notification: Notification = send(subject)
        notificationRepository.save(notification)
    }

    private fun send(subject: Subject): Notification {
        val notification = Notification()
        if (subject is Contract) {
            if (subject.state?.name == "unanswered" ) {
                val name: String? = subject.organizer?.firstName
                val profile: Profile? = subject.musician
                notification.message = "$name has sent you a contract"
                notification.profile = profile
            }
            if (subject.state?.name == "in progress") {
                val name: String? = subject.musician?.firstName
                val profile: Profile? = subject.organizer
                notification.message = "$name has accepted you a contract"
                notification.profile = profile
            }
            if (subject.state?.name == "cancelled") {
                val name: String? = subject.musician?.firstName
                val profile: Profile? = subject.organizer
                notification.message = "$name has rejected you a contract"
                notification.profile = profile
            }
            if (subject.state?.name == "finalized") {
                val profile2: Profile? = subject.organizer
                val notification2 = Notification()
                notification2.message = "Your contract has ended"
                notification2.profile = profile2
                notificationRepository.save(notification2)
                val profile: Profile? = subject.musician
                notification.message = "Your contract has ended"
                notification.profile = profile
            }
        }
        if (subject is Following) {
            val name: String? = subject.follower?.firstName
            val profile: Profile? = subject.followed
            notification.message = "$name has started to follow you"
            notification.profile = profile
        }
        if (subject is Message) {
            val name: String? = subject.sender?.firstName
            val profile: Profile? = subject.receiver
            notification.message = "$name has sent you a message"
            notification.profile = profile
        }
        if (subject is Comment) {
            val name: String? = subject.commenter?.firstName
            val profile: Profile? = subject.publication?.musician
            notification.message = "$name has commented on your publication"
            notification.profile = profile
        }
        return notification
    }
}