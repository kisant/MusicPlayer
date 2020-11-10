package com.example.musicplayer2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    private val mSongNames = ArrayList<String>()
    private val mSongs = ArrayList<String>()
    private val mImages = ArrayList<String>()
    private val mSongArtist = ArrayList<String>()
    private val mGroupInfo = ArrayList<String>()
    private lateinit var mDatabase: DatabaseReference
    var list: ArrayList<Music> = ArrayList()
    var lMusic = list.toMutableList()
    lateinit var adapter: RecyclerViewAdapter
    var fontPath: String = ""
    /* Two type of sort: by artist and by song name
    *  false: by artist
    *  true: by song name */
    var sort: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getMusic(sort, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == 1) {
            fontPath = data.getStringExtra("fontPath")
            getMusic(sort, null)
        }
        if (requestCode == 2) {
            getMusic(sort, null)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.activity_main_actions, menu)

        /* Song search */
        val item: MenuItem = menu!!.findItem(R.id.action_search)
        val searchView: SearchView = MenuItemCompat.getActionView(item) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getMusic(sort, query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        true

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                sort = sort.not()
                getMusic(sort, null)
                true
            }
            R.id.action_search -> {
                false
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                this.startActivityForResult(intent, 1)
                true
            }
            R.id.action_add -> {
                val intent = Intent(this, AddActivity::class.java)
                this.startActivityForResult(intent, 2)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /* Get music from firebase */
    private fun getMusic(sort: Boolean, filter: String?) {
        list.clear()
        val music = Music.create()
        mDatabase = FirebaseDatabase.getInstance().getReference().child("music")

        mDatabase.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot) {
                for (snapshot in p0.children) {
                    val post = snapshot.getValue(music::class.java)
                    list.add(post!!)
                }
                lMusic = list.toMutableList()
                if (filter != null) {
                    lMusic = lMusic.filter { it.songName == filter }.toMutableList()
                }
                if (sort) {
                    lMusic.sortBy { it.songName }
                }
                else {
                    lMusic.sortBy { it.songArtist }
                }
                initRecyclerView(lMusic, fontPath)

//                music.imageUrl = p0.child("imageUrl").getValue().toString()
//                music.songArtist = p0.child("songArtist").getValue().toString()
//                music.songLocationName = p0.child("songLocationName").getValue().toString()
//                music.songName = p0.child("songName").getValue().toString()
//                music.videoId = p0.child("videoId").getValue().toString()
//                list.add(music)
            }
        })
    }
//    private fun initImageBitmaps() {
//        mGroupInfo.add("Pw-0pbY9JeU")
//        mImages.add("https://upload.wikimedia.org/wikipedia/en/7/7d/Blurryface_by_Twenty_One_Pilots.png")
//        mSongs.add("ride")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("Ride")
//
//        mGroupInfo.add("VGMmSOsNAdc")
//        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
//        mSongs.add("pet_cheetah")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("Pet Cheetah")
//
//        mGroupInfo.add("8mn-FFjIbo8")
//        mImages.add("https://muzonov.net/uploads/posts/2018-08/medium/1535531575_1.jpg")
//        mSongs.add("my_blood")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("My Blood")
//
//        mGroupInfo.add("pXRviuL6vMY")
//        mImages.add("https://upload.wikimedia.org/wikipedia/en/7/7d/Blurryface_by_Twenty_One_Pilots.png")
//        mSongs.add("stressed_out")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("Stressed Out")
//
//        mGroupInfo.add("eJnQBXmZ7Ek")
//        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
//        mSongs.add("chlorine")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("Chlorine")
//
//        mGroupInfo.add("OmL9TqTFIAc")
//        mImages.add("https://cdn1.ozone.ru/multimedia/c650/1024330843.jpg")
//        mSongs.add("morph")
//        mSongArtist.add("Twenty One Pilots")
//        mSongNames.add("Morph")
//        nitRecyclerView()
//    }

    private fun initRecyclerView(data: List<Music>, fontPathData: String?) {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        adapter = RecyclerViewAdapter(data, this, fontPathData)
        //val adapter = RecyclerViewAdapter(mImages, mSongs, mSongNames, mSongArtist, mGroupInfo, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}