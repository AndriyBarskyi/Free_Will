package com.example.freewill

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class  SettingActivity : AppCompatActivity() {
    private val mLanguageCodeEn = "en"
    private val mLanguageCodeUa = "ua"
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)


        //Change English to French when user clicked the button.
        val a : Button = findViewById(R.id.english_language)
        val b : Button = findViewById(R.id.ukraine_language)

        b.setOnClickListener(View.OnClickListener { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeUa)
            recreate()
        })

        a.setOnClickListener(View.OnClickListener { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeEn)
            recreate()
        })


    }
}


