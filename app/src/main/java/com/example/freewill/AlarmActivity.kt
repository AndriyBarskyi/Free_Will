package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.databinding.ActivityAlarmBinding

class AlarmActivity : AppCompatActivity() {
    val a = SettingActivity()
    lateinit var binding: ActivityAlarmBinding
    var v: Vibrator? = null
    private var fiveMedia: MediaPlayer? = null
    private var tenMedia: MediaPlayer? = null
    private var fifteenMedia: MediaPlayer? = null
    private var twelveMedia: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun soundPlay(sound: MediaPlayer, resLang: SharedPreferences, count:Int) {
        v = getSystemService(VIBRATOR_SERVICE) as Vibrator?
        when(count){
            0->{sound.start()}
            2->{v!!.vibrate(VibrationEffect.createOneShot(5000,
                VibrationEffect.DEFAULT_AMPLITUDE))}
        }
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
        val resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)
        val count = resLang.getInt("count", 1)

        if (resLang?.getBoolean(a.five, false) == true)
        {
            val med = randomAuduo(R.raw.yulik,R.raw.ksenia)
            fiveMedia = MediaPlayer.create(this, med)
            binding.AlarmText.text = getString(R.string.rest_five)
            soundPlay(fiveMedia!!, resLang, count)
        }
        if (resLang?.getBoolean(a.ten, false) == true)
        {
            val med = randomAuduo(R.raw.diana,R.raw.andriy)
            tenMedia = MediaPlayer.create(this, med)
            binding.AlarmText.text = getString(R.string.rest_ten)
            soundPlay(tenMedia!!, resLang, count)
        }
        if (resLang?.getBoolean(a.fifteen, false) == true)
        {
            val med = randomAuduo(R.raw.olenka,R.raw.marta)
            fifteenMedia = MediaPlayer.create(this, med)
            binding.AlarmText.text = getString(R.string.rest_fifteen)
            soundPlay(fifteenMedia!!, resLang,count)
        }
        if (resLang?.getBoolean(a.twelve, false) == true)
        {
            val med = randomAuduo(R.raw.olesya,R.raw.andrew)
            twelveMedia = MediaPlayer.create(this, med)
            binding.AlarmText.text = getString(R.string.rest_twelve)
            soundPlay(twelveMedia!!, resLang,count)
        }


    }
    fun randomAuduo(first:Int, second:Int): Int {
        when((1..2).random()){
            1->{return first}
            2->{return second}
        }
        return second
    }
    override fun onDestroy() {
        super.onDestroy()
    }
}