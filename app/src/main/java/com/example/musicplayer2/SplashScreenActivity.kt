package com.example.musicplayer2

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import gr.net.maroulis.library.EasySplashScreen

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity::class.java)
                .withSplashTimeOut(5000)
                .withBackgroundColor(Color.parseColor("#1C1C1C"))
                .withLogo(R.drawable.splash_screen_logo)

        val easySplashScreen:View = config.create()

        setContentView(easySplashScreen)
    }
}
