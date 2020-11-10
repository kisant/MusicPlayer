package com.example.musicplayer2

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Music::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun musicDao(): MusicDao?

    companion object {
        private var instance: AppDatabase? = null
        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "music_database")
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return instance as AppDatabase
        }
    }

    private val roomCallback: Callback = object : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            PopulateDbAsyncTask(instance!!).execute()
        }
    }

    private class PopulateDbAsyncTask(db: AppDatabase) : AsyncTask<Void?, Void?, Void?>() {
        private val musicDao: MusicDao
        lateinit var music: Music
        protected override fun doInBackground(vararg voids: Void?): Void? {
            music.songLocationName = "test01"
            music.songName = "id_test01"
            music.songArtist = "test01"
            music.imageUrl = "test01"
            music.videoId = "test01"
            musicDao.insert(music)
            return null
        }

        init {
            musicDao = db.musicDao()!!
        }
    }
}