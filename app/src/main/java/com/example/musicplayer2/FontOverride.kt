package com.example.musicplayer2

import android.app.Application

class FontOverride : Application() {
    override fun onCreate() {
        super.onCreate()
        TypefaceUtil.overrideFont(
                applicationContext,
                "SANS-SERIF",
                ""
        )
    }
}