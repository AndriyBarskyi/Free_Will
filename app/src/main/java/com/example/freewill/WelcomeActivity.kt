package com.example.freewill

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class WelcomeActivity : AppCompatActivity() {
    lateinit var chooseLang : String

    private val SPLASH_TIME: Long = 3000
    override fun onCreate(savedInstanceState: Bundle?) {
        val a = SettingActivity()
        a.resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)
        LocaleHelper.setLocale(this, a.resLang?.getString(a.keyLanguage, a.mLanguageCodeUa)!!)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        Handler().postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, SPLASH_TIME)

    }
}