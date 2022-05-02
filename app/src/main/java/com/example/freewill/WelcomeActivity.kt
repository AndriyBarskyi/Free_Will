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
    val editor = checking?.edit()
    val checkk = "check"

    fun toSettingApp()
    {
        a.resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)
        LocaleHelper.setLocale(this, a.resLang?.getString(a.keyLanguage, a.mLanguageCodeUa)!!)
        when(a.resLang?.getString(a.keyFont, a.medium)!!){
            a.small->{SetSizeFont(a.smallSize)}
            a.medium->{SetSizeFont(a.mediumSize)}
            a.big->{SetSizeFont(a.bigSize) }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        toSettingApp()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
/*
        checking = getSharedPreferences("forSett", Context.MODE_PRIVATE)
        val choose = checking?.getBoolean(checkk, true)!!
        if (choose){
            when(a.resLang?.getString(a.keyFont, a.medium)!!){
                a.small->{SetSizeFont(a.smallSize)}
                a.medium->{SetSizeFont(a.mediumSize)}
                a.big->{SetSizeFont(a.bigSize)}
            }
            editor?.putBoolean(checkk, false)
            editor?.apply()
        }
        editor?.putBoolean(checkk, true)
        editor?.apply()
*/



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