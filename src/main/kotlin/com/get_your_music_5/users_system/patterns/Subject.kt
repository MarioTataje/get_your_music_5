package com.get_your_music_5.users_system.patterns

interface Subject {
    fun addObserver(o: Observer)
    fun notifyObservers()
}