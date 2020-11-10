package com.example.musicplayer2

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class MusicViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: MusicRepository
    val allMusics: LiveData<List<Music?>?>?

    fun insert(music: Music) {
        repository.insert(music)
    }

    fun update(music: Music?) {
        repository.update(music)
    }

    fun delete(music: Music?) {
        repository.delete(music)
    }

    fun deleteAllMusics() {
        repository.deleteAllMusics()
    }

    init {
        repository = MusicRepository(application)
        allMusics = repository.allMusics
    }
}