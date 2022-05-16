package com.example.freewill

import android.media.MediaPlayer
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AlarmActivity : AppCompatActivity() {
    var ringtone: Ringtone? = null
    var fiveMedia: MediaPlayer? = null

    fun soundPlay(sound: MediaPlayer) {
        sound.start()
    }
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)
        /*
        var notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, notificationUri)
        if (ringtone == null) {
            notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ringtone = RingtoneManager.getRingtone(this, notificationUri)
        }
        if (ringtone != null) {
            //ringtone!!.play()
            soundPlay(fiveMedia)
        }
         */
        fiveMedia = MediaPlayer.create(this, R.raw.audio_five_minutes)
        soundPlay(fiveMedia!!)

    }

    override fun onDestroy() {
        fiveMedia!!.stop()
        super.onDestroy()
    }
}