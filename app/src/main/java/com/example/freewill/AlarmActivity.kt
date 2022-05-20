package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.databinding.ActivityAlarmBinding
import com.example.freewill.databinding.ActivitySettingBinding


import android.content.Intent

import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ReadFirebase
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import android.widget.Button
import android.app.AlarmManager
import android.app.PendingIntent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.*

class AlarmActivity : AppCompatActivity() {
    val a = SettingActivity()
    lateinit var binding: ActivityAlarmBinding
    private var fiveMedia: MediaPlayer? = null
    private var tenMedia: MediaPlayer? = null
    private var fifteenMedia: MediaPlayer? = null
    private var twelveMedia: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun soundPlay(sound: MediaPlayer, resLang: SharedPreferences) {
        sound.start()
        val editor = resLang.edit()
        var index = resLang.getInt(a.clockIndex,0)
        index +=1
        editor?.putBoolean(a.period, true)
        editor?.apply()
        if (index != a.timesTo.size)
        {
            editor?.putInt(a.clockIndex, index)
            editor?.apply()
        }
        else
        {
            editor?.putInt(a.clockIndex, 0)
            editor?.apply()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fiveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        tenMedia = MediaPlayer.create(this, R.raw.audio_ten_minut)
        fifteenMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        twelveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        val resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)

        if (resLang?.getBoolean(a.five, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_five)
            soundPlay(fiveMedia!!, resLang)
        }
        if (resLang?.getBoolean(a.ten, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_ten)
            soundPlay(tenMedia!!, resLang)
        }
        if (resLang?.getBoolean(a.fifteen, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_fifteen)
            soundPlay(fifteenMedia!!, resLang)
        }
        if (resLang?.getBoolean(a.twelve, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_twelve)
            soundPlay(twelveMedia!!, resLang)
        }
    }
    override fun onDestroy() {
        fiveMedia!!.stop()
        super.onDestroy()
    }
}