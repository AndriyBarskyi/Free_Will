package com.example.freewill

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics

class WelcomeActivity : AppCompatActivity() {

    private val SPLASH_TIME: Long = 3000
    val a = SettingActivity()
    var checking : SharedPreferences? = null

    fun toSettingApp()
    {
        val resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)
        val r = resLang.getString(a.keyLanguage, a.mLanguageCodeUa)
        LocaleHelper.setLocale(this, r)
        val t = resLang?.getString(a.keyFont, a.medium)!!
        when(t){
            a.small->{SetSizeFont(a.smallSize)}
            a.medium->{SetSizeFont(a.mediumSize)}
            a.big->{SetSizeFont(a.bigSize) }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        toSettingApp()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_TIME)

    }

    fun SetSizeFont(size_coef: Float)
    {
        val configuration = resources.configuration
        configuration.fontScale = size_coef //0.85 small size, 1 normal size, 1,15 big etc


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)

    }
}
