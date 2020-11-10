package com.example.musicplayer2

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.MediaController
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.info.*


class InfoActivity : AppCompatActivity() {
    lateinit var video: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info)

        /* Back navigation button */
        val actionbar = supportActionBar
        actionbar!!.title = "Song"
        actionbar.setDisplayHomeAsUpEnabled(true)

        //val videoIdr = getIncomingIntent()
        val filmVideo: String = intent.getStringExtra("videoId")

        video = findViewById(R.id.video)
        val mediaController = MediaController(this)
        mediaController.setAnchorView(video)
        val uri = Uri.parse(filmVideo)
        video.setMediaController(mediaController)
        video.setVideoURI(uri)
        video.requestFocus()
        video.start()


//        if (!filmVideo.isEmpty()) {
//            val videoView = YouTubePlayerView(this)
//            videoView.setLayoutParams(
//                    LinearLayout.LayoutParams(
//                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                            LinearLayout.LayoutParams.WRAP_CONTENT,
//                            0.33f
//                    ))
//            videoView.getPlayerUiController().showFullscreenButton(true)
//            videoView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
//                override fun onReady(youTubePlayer: YouTubePlayer) {
//                    val videoId = filmVideo
//                    youTubePlayer.cueVideo(filmVideo, 0f)
//                }
//            })
//
//            videoView.getPlayerUiController().setFullScreenButtonClickListener(View.OnClickListener {
//                if (videoView.isFullScreen()) {
//                    videoView.exitFullScreen()
//                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//                    // Show ActionBar
//                    if (supportActionBar != null) {
//                        supportActionBar!!.show()
//                    }
//                } else {
//                    videoView.enterFullScreen()
//                    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                    // Hide ActionBar
//                    if (supportActionBar != null) {
//                        supportActionBar!!.hide()
//                    }
//                }
//            })
//            linLayout.addView(videoView)
//        }

        /* Playing video by VideoView */
//        val video: VideoView = findViewById(R.id.video)
//        val media = MediaController(this)
//        media.setAnchorView(video)
//        val uri = Uri.parse("rtsp://r5---sn-4g5e6nsk.googlevideo.com/Cj0LENy73wIaNAnlJT22pbQPPxMYESARFC3OjWleMOCoAUIASARg2auCjZSJofldigELUUwwemtqVkxHSE0M/9203A4B50F3E9606C7ABBBE9080D6CAB79008ED2.505A2D54B798A32B41E12C3E15E1BF85ABB3EE58/yt8/1/video.3gp")
//        video.setMediaController(media)
//        video.setVideoURI(uri)
//        video.requestFocus()
//        video.start()

        /* Playing video by special dependency */
//        val youTubePlayerView: YouTubePlayerView = findViewById(R.id.youtube_player_view)
//        lifecycle.addObserver(youTubePlayerView)

        /* Playing video by webview */
//        val webView: WebView = findViewById(R.id.video)
//        webView.settings.javaScriptEnabled = true
//        webView.settings.loadWithOverviewMode = true
//        webView.settings.useWideViewPort = true
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//                view.loadUrl(url)
//                return true
//            }
//
//            override fun onPageFinished(view: WebView, url: String) {
//            }
//        }
//        webView.loadUrl("https://www.youtube.com/watch?v=Pw-0pbY9JeU")
    }


    /* Back button fun */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

//    /* Incoming content from RecyclerAdapter */
//    private fun getIncomingIntent() : String {
//        if (intent.hasExtra("video")) {
//            val infoGroup = intent.getStringExtra("video")
//
//            return infoGroup
//        }
//        return ""
//    }
//
//    /* Set info text from intent */
//    private fun setInfoGroup(infoGroup: String) {
//        val sInfoGroup: TextView = findViewById(R.id.infoGroup)
//        sInfoGroup.text = infoGroup
//    }
}