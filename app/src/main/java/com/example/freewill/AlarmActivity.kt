package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.databinding.ActivityAlarmBinding
import com.example.freewill.databinding.ActivitySettingBinding

class AlarmActivity : AppCompatActivity() {
    val a = SettingActivity()
    lateinit var binding: ActivityAlarmBinding
    private var fiveMedia: MediaPlayer? = null
    private var tenMedia: MediaPlayer? = null
    private var fifteenMedia: MediaPlayer? = null
    private var twelveMedia: MediaPlayer? = null

    fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fiveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        tenMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        fifteenMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        twelveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        val resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)

        if (resLang?.getBoolean(a.five, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_five)
            soundPlay(fiveMedia!!)
        }
        if (resLang?.getBoolean(a.ten, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_ten)
            soundPlay(tenMedia!!)
        }
        if (resLang?.getBoolean(a.fifteen, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_fifteen)
            soundPlay(fifteenMedia!!)
        }
        if (resLang?.getBoolean(a.twelve, false) == true)
        {
            binding.AlarmText.setText(R.string.rest_twelve)
            soundPlay(twelveMedia!!)
        }
    }
    override fun onDestroy() {
        fiveMedia!!.stop()
        super.onDestroy()
    }
}