package com.example.musicplayer2

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData


class MusicRepository(application: Application?) {
    private val musicDao: MusicDao
    val allMusics: LiveData<List<Music?>?>?

    fun insert(music: Music?) {
        InsertMusicAsyncTask(musicDao).execute(music)
    }

    fun update(music: Music?) {
        UpdateMusicAsyncTask(musicDao).execute(music)
    }

    fun delete(music: Music?) {
        DeleteMusicAsyncTask(musicDao).execute(music)
    }

    fun deleteAllMusics() {
        DeleteAllMusicsAsyncTask(musicDao).execute()
    }

    private class InsertMusicAsyncTask(musicDao: MusicDao) : AsyncTask<Music?, Void?, Void?>() {
        private val musicDao: MusicDao
        protected override fun doInBackground(vararg musics: Music?): Void? {
            musicDao.insert(musics[0])
            return null
        }

        init {
            this.musicDao = musicDao
        }
    }

    private class UpdateMusicAsyncTask(musicDao: MusicDao) : AsyncTask<Music?, Void?, Void?>() {
        private val musicDao: MusicDao
        protected override fun doInBackground(vararg musics: Music?): Void? {
            musicDao.update(musics[0])
            return null
        }

        init {
            this.musicDao = musicDao
        }
    }

    private class DeleteMusicAsyncTask(musicDao: MusicDao) : AsyncTask<Music?, Void?, Void?>() {
        private val musicDao: MusicDao
        protected override fun doInBackground(vararg musics: Music?): Void? {
            musicDao.delete(musics[0])
            return null
        }

        init {
            this.musicDao = musicDao
        }
    }

    private class DeleteAllMusicsAsyncTask(musicDao: MusicDao) : AsyncTask<Void?, Void?, Void?>() {
        private val musicDao: MusicDao
        protected override fun doInBackground(vararg voids: Void?): Void? {
            musicDao.deleteAllMusics()
            return null
        }

        init {
            this.musicDao = musicDao
        }
    }

    init {
        val database = AppDatabase.getInstance(application!!)
        musicDao = database!!.musicDao()!!
        allMusics = musicDao.getAllMusics()
    }
}