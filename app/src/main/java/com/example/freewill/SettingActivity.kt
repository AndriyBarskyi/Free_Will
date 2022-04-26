package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.databinding.ActivitySettingBinding
import com.google.android.material.navigation.NavigationView


class SettingActivity : AppCompatActivity()
{
    lateinit var bindingClass:ActivitySettingBinding
    private val mLanguageCodeEn = "en"
    private val mLanguageCodeUa = "ua"
    lateinit var chooseLang : String
    val nameBaseLang = "LANGUAGE"
    val path = "chooseLang"
    var resLang : SharedPreferences? = null

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        resLang = getSharedPreferences(nameBaseLang, Context.MODE_PRIVATE)
        chooseLang = resLang?.getString( path, mLanguageCodeUa)!!
        LocaleHelper.setLocale(this, chooseLang)

        super.onCreate(savedInstanceState)
        bindingClass = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)


        bindingClass.ukraineLanguage.setOnClickListener(View.OnClickListener
        { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeUa)
            chooseLang = mLanguageCodeUa
            recreate()
        })

        bindingClass.englishLanguage.setOnClickListener(View.OnClickListener
        { //Change Application level locale
            LocaleHelper.setLocale(this, mLanguageCodeEn)
            chooseLang = mLanguageCodeEn
            recreate()
        })

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: NavigationView = findViewById(R.id.navView)
        navView.itemIconTintList = null

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> startActivity(Intent(this, ScheduleActivity::class.java))

                R.id.nav_map -> startActivity(Intent(this, MapActivity::class.java))

                R.id.nav_settings -> startActivity(Intent(this, SettingActivity::class.java))

            }
            true
        }
    }

    fun saveLanguage(res:String)
    {
        val editor = resLang?.edit()
        editor?.putString(path, res)
        editor?.apply()

    }

    override fun onDestroy()
    {
        super.onDestroy()
        saveLanguage(chooseLang)

    }
}


