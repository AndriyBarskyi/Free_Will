package com.example.freewill

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class WelcomeActivity : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    private val SPLASH_TIME: Long = 3000
    val a = SettingActivity()
    var checking : SharedPreferences? = null

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
        firebaseAuth = FirebaseAuth.getInstance()


        Handler().postDelayed({
            checkUser()
            finish()
        }, SPLASH_TIME)


    }

    fun SetSizeFont(size_coef: Float)
    {
        val configuration = resources.configuration
        configuration.fontScale = size_coef


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)

    }

    private fun checkUser() {
        val user = firebaseAuth.currentUser
        if(user != null){
            if(user!!.isEmailVerified)
            {
                startActivity(Intent(this, ScheduleActivity::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
}