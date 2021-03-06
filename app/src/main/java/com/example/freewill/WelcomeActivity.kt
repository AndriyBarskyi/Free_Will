package com.example.freewill

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.DisplayMetrics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class WelcomeActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth

    private val SPLASH_TIME: Long = 3000
    val a = SettingActivity()

    fun toSettingApp()
    {
        a.resLang = getSharedPreferences(a.baseForSetting, Context.MODE_PRIVATE)
        LocaleHelper.setLocale(this, a.resLang?.getString(a.keyLanguage, a.mLanguageCodeUa)!!)
        when(a.resLang!!.getString(a.keyFont, a.medium)!!){
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

        Handler(Looper.getMainLooper()).postDelayed({
            checkUser()
            finish()
        }, SPLASH_TIME)

        val imageViewOnTouchListener = View.OnLongClickListener{
            val myDialogFragment = MyDialogFragment()
            val manager = supportFragmentManager
            myDialogFragment.show(manager, "myDialog")
            true
        }
        val imageSecret: ImageView = findViewById(R.id.imageView4)
        imageSecret.setOnLongClickListener(imageViewOnTouchListener)
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

    //check whether user is signed in
    private fun checkUser() {
        val user = firebaseAuth.currentUser
        if(user != null){
            startActivity(Intent(this, ScheduleActivity::class.java))
            finish()
        }
        else{
            startActivity(Intent(this, GuestOrStudentActivity::class.java))
            finish()
        }
    }

    class MyDialogFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("????????????????, ?????? ?????? ???????????????? ??????????!")
                    .setIcon(R.drawable.tarasuk)
                    .setPositiveButton("??????????????") {
                            dialog, id ->  dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
}
