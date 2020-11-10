package com.example.musicplayer2

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.player.*

class PlayerActivity : AppCompatActivity() {
    private lateinit var mp: MediaPlayer
    private var totalTime: Int = 0
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player)

        /* Back navigation button */
        val actionbar = supportActionBar
        actionbar!!.title = "Songs"
        actionbar.setDisplayHomeAsUpEnabled(true)

        var song = getIncomingIntent()
        val videoId = intent.getStringExtra("videoId")

        /* Initialize and create onClick fun button info */
        button = findViewById(R.id.infoBtn)
        button.setOnClickListener {
            val intent = Intent(this, InfoActivity::class.java)
            intent.putExtra("videoId", videoId)
            this.startActivity(intent)
        }

        /* Music play */
        var sound_id: Int = this.getResources().getIdentifier(song, "raw",
                this.getPackageName())
        var musicUri: Uri = Uri.parse("R.raw.$song")

        mp = MediaPlayer.create(this, sound_id)
        mp.isLooping = true
        mp.setVolume(0.5f, 0.5f)
        totalTime = mp.duration

        volumeBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener{
                    override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                    ) {
                        if (fromUser) {
                            var volumeNum = progress / 100.0f
                            mp.setVolume(volumeNum, volumeNum)
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
        )

        positionBar.max = totalTime
        positionBar.setOnSeekBarChangeListener(
                object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(
                            seekBar: SeekBar?,
                            progress: Int,
                            fromUser: Boolean
                    ) {
                        if (fromUser) {
                            mp.seekTo(progress)
                        }
                    }
                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    }
                }
        )

        Thread(Runnable {
            while (mp != null) {
                try {
                    var msg = Message()
                    msg.what = mp.currentPosition
                    handler.sendMessage(msg)
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {

                }
            }
        }).start()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getIncomingIntent() : String {
        if (intent.hasExtra("imageUrl") && intent.hasExtra("songLocationName")
                && intent.hasExtra("songName")
                && intent.hasExtra("songArtist")) {
            val imageUrl = intent.getStringExtra("imageUrl")
            val songName = intent.getStringExtra("songName")
            val songArtist = intent.getStringExtra("songArtist")
            val song = intent.getStringExtra("songLocationName")

            setImage(imageUrl, songName, songArtist)

            return song
        }
        return ""
    }

    private fun setImage(imageUrl: String, songName: String, songArtist: String) {
        val sName: TextView = findViewById(R.id.song_name)
        sName.text = songName
        val sArtist: TextView = findViewById(R.id.song_artist)
        sArtist.text = songArtist
        val image: ImageView = findViewById(R.id.image)
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image)
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            positionBar.progress = currentPosition

            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time: Int): String {
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) {
            timeLabel += "0"
        }
        timeLabel += sec

        return timeLabel
    }

    fun playBtnClick(v: View) {
        if(mp.isPlaying) {
            mp.pause()
            playBtn.setBackgroundResource(R.drawable.play)
        }
        else {
            mp.start()
            playBtn.setBackgroundResource(R.drawable.pause)
        }
    }
}

