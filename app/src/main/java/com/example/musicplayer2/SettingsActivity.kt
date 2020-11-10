package com.example.musicplayer2


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import java.util.*


lateinit var rbEngLang: RadioButton
lateinit var rbIndLang: RadioButton
lateinit var rgLanguages: RadioGroup
lateinit var rbDefaultFont: RadioButton
lateinit var rbInderFont: RadioButton
lateinit var rgFonts: RadioGroup

class SettingsActivity : AppCompatActivity() {
    /*Seek-bar settings*/
    val start_value = 0.7f
    val step = 0.15f
    var size_coef = 0
    lateinit var twCofirmTextSize: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        loadAppLocale()
        setContentView(R.layout.settings)

        /* Try to change action-bar */
        val ab: ActionBar? = supportActionBar
        ab!!.title = resources.getString(R.string.app_name)

        /* Back navigation button */
        val actionbar = supportActionBar
        actionbar!!.title = "Settings"
        actionbar.setDisplayHomeAsUpEnabled(true)

        /* Language button initialization */
        rgLanguages = findViewById(R.id.languages)
        rbIndLang = findViewById(R.id.indus_lang)
        rbEngLang = findViewById(R.id.eng_lang)

        /* Font button initialization */
        rgFonts = findViewById(R.id.fonts)
        rbDefaultFont = findViewById(R.id.defaultFont)
        rbInderFont = findViewById(R.id.inderFont)

        /* Textsize change */
        val seekBar = findViewById(R.id.seekBar) as SeekBar

        if (seekBar != null) {
            seekBar.progress = 5
            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    size_coef = progress
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })
        }

        /* Confirm text size changing */
        twCofirmTextSize = findViewById(R.id.confirmTextSize)
        twCofirmTextSize.setOnClickListener{

            val settings = getSharedPreferences("MyAppSett", Context.MODE_PRIVATE)
            val value_add = settings.edit()

            value_add.putInt("size_coef", size_coef)
            value_add.apply()

            val res = resources
            val сoef = start_value + size_coef * step

            val configuration = Configuration(res.configuration)
            configuration.fontScale = сoef
            res.updateConfiguration(configuration, res.displayMetrics)

        }
    }

    /* Back button fun */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    /* Font click button logic */
    fun setDefaultFont(view: View) {
        TypefaceUtil.overrideFont(
            applicationContext,
            "SERIF",
            "font/sans_serif.ttf"
        )
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fontPath", "font/sans_serif.ttf")
        setResult(Activity.RESULT_OK, intent)
    }

    fun setInderFont(view: View) {
        TypefaceUtil.overrideFont(
            getApplicationContext(),
            "SERIF",
            "font/inder.ttf"
        )
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("fontPath", "font/inder.ttf")
        setResult(Activity.RESULT_OK, intent)

        val settings = getSharedPreferences("MyAppSett", Context.MODE_PRIVATE)
        val value_add = settings.edit()

        value_add.putInt("size_coef", size_coef)
        value_add.apply()

        val res = resources
        val сoef = start_value + size_coef * step

        val configuration = Configuration(res.configuration)
        configuration.fontScale = сoef
        res.updateConfiguration(configuration, res.displayMetrics)
    }

    /* Language click button logic */
    fun setEngLang(view: View) {
        setAppLocale("en")
        recreate()
    }

    fun setIndLang(view: View) {
        setAppLocale("hi")
        recreate()
    }

    private fun setAppLocale(language: String?) {
        /* Working version */
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.setLocale(Locale(language))
        res.updateConfiguration(conf, dm)

        /* Testing version  */
//        val locale = Locale(language)
//        Locale.setDefault(locale)
//
//        val config = Configuration()
//        config.locale = locale
//        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
//
//        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
//        editor.putString("Language", language)
//        editor.apply()
    }

    private fun loadAppLocale() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("Language", "hi")
        setAppLocale(language)
    }
}