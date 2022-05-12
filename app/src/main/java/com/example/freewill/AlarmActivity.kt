package com.example.freewill

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {
    val a = SettingActivity()
    private var fiveMedia: MediaPlayer? = null
    private var tenMedia: MediaPlayer? = null
    private var fifteenMedia: MediaPlayer? = null
    private var twelveMedia: MediaPlayer? = null

    fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        fiveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        tenMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        fifteenMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        twelveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        if (a.bindingClass.fiveMinute.isChecked)
            soundPlay(fiveMedia!!)
        if (a.bindingClass.fiveMinute.isChecked)
            soundPlay(tenMedia!!)
        if (a.bindingClass.fiveMinute.isChecked)
            soundPlay(fifteenMedia!!)
        if (a.bindingClass.fiveMinute.isChecked)
            soundPlay(twelveMedia!!)
    }

    override fun onDestroy() {
        fiveMedia!!.stop()
        super.onDestroy()
    }
}