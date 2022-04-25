package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class  SettingActivity : AppCompatActivity() {
    private val mLanguageCodeEn = "en"
    private val mLanguageCodeUa = "ua"
    lateinit var chooseLang : String
    var resLang : SharedPreferences? = null

    @SuppressLint("ResourceAsColor")
    protected override fun onCreate(savedInstanceState: Bundle?)
    {
        resLang = getSharedPreferences("LANGUAGE", Context.MODE_PRIVATE)
        chooseLang = resLang?.getString("chooseLang", mLanguageCodeUa)!!
        LocaleHelper.setLocale(this, chooseLang)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        val a : Button = findViewById(R.id.english_language)
        val b : Button = findViewById(R.id.ukraine_language)

        b.setOnClickListener(View.OnClickListener { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeUa)
            chooseLang = mLanguageCodeUa
            recreate()
        })

        a.setOnClickListener(View.OnClickListener { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeEn)
            chooseLang = mLanguageCodeEn
            recreate()
        })
    }

    fun saveLanguage(res:String)
    {
        val editor = resLang?.edit()
        editor?.putString("chooseLang", res)
        editor?.apply()
    }

    override fun onDestroy()
    {
        super.onDestroy()
        saveLanguage(chooseLang)

    }
}


