package com.example.musicplayer2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask


class AddActivity : AppCompatActivity() {
    private val REQUEST_CODE_READ_EXTERNAL_PERMISSION = 2
    private val REQUEST_CODE_SELECT_VIDEO_FILE = 1

    private lateinit var songName: EditText
    private lateinit var artist: EditText
    private lateinit var imageUrl: EditText
    private lateinit var btnAdd: Button
    private lateinit var btnChoose: Button
    private lateinit var btnUpload:Button
    private lateinit var video: VideoView

    private lateinit var reference: DatabaseReference
    private lateinit var storage: StorageReference
    private lateinit var storageTask: StorageTask<*>

    private lateinit var music: Music
    private lateinit var videoUri: Uri
    private lateinit var videoPath: String

    private lateinit var musicViewModel: MusicViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_item)

        musicViewModel = ViewModelProviders
                .of(this)
                .get<MusicViewModel>(MusicViewModel::class.java)
        musicViewModel.allMusics!!.observe(this, object : Observer<List<Music?>?> {
            override fun onChanged(t: List<Music?>?) {
                t?.size
            }
        })

        songName = findViewById(R.id.etSongName)
        artist = findViewById(R.id.etArtist)
        imageUrl = findViewById(R.id.etImageUrl)
        btnAdd = findViewById(R.id.btnAdd)
        btnChoose = findViewById(R.id.btnChooseFile)
        btnUpload = findViewById(R.id.btnUpload)
        video = findViewById(R.id.video)
        music = Music.create()
        reference = FirebaseDatabase.getInstance().reference.child("music")
        storage = FirebaseStorage.getInstance().getReference("videos");

        btnAdd.setOnClickListener {
            music.songName = songName.text.toString().trim()
            music.songArtist = artist.text.toString().trim()
            music.imageUrl = imageUrl.text.toString().trim()
            music.songLocationName = "ride"

            musicViewModel.insert(music)

            reference.push().setValue(music)

            val intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, intent)

            Toast.makeText(this@AddActivity, "Song was added to your player.", Toast.LENGTH_SHORT).show();
        }

        btnChoose.setOnClickListener{
            val readExternalStoragePermission = ContextCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE)

            if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                val requirePermission = arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE)
                ActivityCompat.requestPermissions(this,
                        requirePermission,
                        REQUEST_CODE_READ_EXTERNAL_PERMISSION)
            }
            else {
                selectVideoFile()
            }
        }

        btnUpload.setOnClickListener {
            uploadFile()
        }
    }

    private fun getFileExtension(uri: Uri): String? {
        val cR = contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri))
    }

    private fun uploadFile() {
        val fileReference = storage.child(System.currentTimeMillis()
                .toString() + "." + getFileExtension(videoUri))
        storageTask = fileReference.putFile(videoUri)

        val urlTask = storageTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            fileReference.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this@AddActivity, "Video was uploaded.", Toast.LENGTH_SHORT).show();
                val downloadUri = task.result
                music.videoId = downloadUri.toString()
            }
        }
    }

    private fun selectVideoFile() {
        val selectVideoIntent = Intent(Intent.ACTION_GET_CONTENT)
        selectVideoIntent.type = "video/*"
        startActivityForResult(selectVideoIntent, REQUEST_CODE_SELECT_VIDEO_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_VIDEO_FILE
                && resultCode == Activity.RESULT_OK) {
            videoUri = data!!.data!!
            videoPath = videoUri.lastPathSegment!!

            if (!TextUtils.isEmpty(videoPath)) {
                if (!videoPath.trim { it <= ' ' }.toLowerCase().startsWith("http")) {
                    video.setVideoURI(videoUri)
                }
                else {
                    val webVideoFileUri = Uri.parse(videoPath.trim { it <= ' ' })
                    video.setVideoURI(webVideoFileUri)
                }
                Toast.makeText(this@AddActivity, "Video was choosed.", Toast.LENGTH_SHORT).show();
                video.visibility = View.VISIBLE
                video.start()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String?>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_PERMISSION) {
            if (grantResults.isNotEmpty()) {
                val grantResult = grantResults[0]
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    selectVideoFile()
                }
                else {
                    Toast.makeText(applicationContext,
                            "You denied read external storage permission.",
                            Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
