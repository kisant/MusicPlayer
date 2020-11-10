package com.example.musicplayer2

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MusicDao {
    @Insert
    fun insert(music: Music?)

    @Update
    fun update(music: Music?)

    @Delete
    fun delete(music: Music?)

    @Query("DELETE FROM music")
    fun deleteAllMusics()

    @Query("SELECT * FROM music ORDER BY song_artist DESC")
    fun getAllMusics(): LiveData<List<Music?>?>?
}