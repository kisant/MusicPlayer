package com.example.musicplayer2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity2 : AppCompatActivity() {
    private val mSongNames = ArrayList<String>()
    private val mSongs = ArrayList<String>()
    private val mImages = ArrayList<String>()
    private val mSongArtist = ArrayList<String>()
    private val mGroupInfo = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initImageBitmaps()
    }

    private fun initImageBitmaps() {
        mGroupInfo.add("Pw-0pbY9JeU")
        mImages.add("https://upload.wikimedia.org/wikipedia/en/7/7d/Blurryface_by_Twenty_One_Pilots.png")
        mSongs.add("ride")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("Ride")

        mGroupInfo.add("VGMmSOsNAdc")
        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
        mSongs.add("pet_cheetah")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("Pet Cheetah")

        mGroupInfo.add("8mn-FFjIbo8")
        mImages.add("https://muzonov.net/uploads/posts/2018-08/medium/1535531575_1.jpg")
        mSongs.add("my_blood")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("My Blood")

        mGroupInfo.add("pXRviuL6vMY")
        mImages.add("https://upload.wikimedia.org/wikipedia/en/7/7d/Blurryface_by_Twenty_One_Pilots.png")
        mSongs.add("stressed_out")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("Stressed Out")

        mGroupInfo.add("eJnQBXmZ7Ek")
        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
        mSongs.add("chlorine")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("Chlorine")

        mGroupInfo.add("OmL9TqTFIAc")
        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
        mSongs.add("morph")
        mSongArtist.add("Twenty One Pilots")
        mSongNames.add("Morph")
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //val adapter = RecyclerViewAdapter(mImages, mSongs, mSongNames, mSongArtist, mGroupInfo, this)
        //recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}