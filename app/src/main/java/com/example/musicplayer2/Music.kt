package com.example.musicplayer2

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Music() {
    companion object Factory {
        fun create(): Music = Music()
    }

    @ColumnInfo(name = "video_id")
    lateinit var videoId: String
    @ColumnInfo(name = "image_url")
    lateinit var imageUrl: String
    @PrimaryKey
    lateinit var songName: String
    @ColumnInfo(name = "song_artist")
    lateinit var songArtist: String
    @ColumnInfo(name = "song_location_name")
    lateinit var songLocationName: String

}