package com.example.musicplayer2

import android.content.Context
import android.graphics.Typeface
import android.util.Log


private const val TAG = "TypefaceUtil"


object TypefaceUtil {
    fun overrideFont(context: Context, defaultFontNameToOverride: String, customFontFileNameInAssets: String) {
        try {
            val customFontTypeface = Typeface.createFromAsset(context.assets, customFontFileNameInAssets)
            val defaultFontTypefaceField = Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
            defaultFontTypefaceField.isAccessible = true
            defaultFontTypefaceField[null] = customFontTypeface
        } catch (e: Exception) {
            Log.e(TAG, "Can not set custom font $customFontFileNameInAssets instead of $defaultFontNameToOverride")
        }
    }
}