package com.example.freewill

import java.text.SimpleDateFormat
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ReadFirebase
import com.example.freewill.models.User
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class SettingActivity : AppCompatActivity()
{
    val baseForSetting = "LANGUAGE"
    val keyLanguage = "chooseLang"
    val keyFont ="font"
    val mLanguageCodeEn = "en"
    val mLanguageCodeUa = "ua"
    val small = "small"
    val medium = "medium"
    val big = "big"
    val smallSize = 0.80f
    val mediumSize = 1.0f
    val bigSize = 1.2f
    val five = "five"
    val ten = "ten"
    val fifteen = "fifteen"
    val twelve = "twelve"
    val hour:IntArray= intArrayOf(8,10,11,13)
    val minute:IntArray= intArrayOf(30,10,50,30)
    val time:IntArray= intArrayOf(5,10,15,20)

    var chooseSizeKoef : Float? = null
    var count :Int?=null
    var result:Boolean?=null
    var resultInt:Int?=null
    lateinit var chooseFont : String
    lateinit var bindingClass: ActivitySettingBinding
    lateinit var chooseLang : String
    var resLang : SharedPreferences? = null
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var firebaseAuth: FirebaseAuth

    fun SetSizeFont(size_coef: Float)
    {
        val configuration = resources.configuration
        configuration.fontScale = size_coef //0.85 small size, 1 normal size, 1,15 big etc


        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
        //TypedValue.COMPLEX_UNIT_DIP
        //

    }


    @SuppressLint("ResourceAsColor", "CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {

        resLang = getSharedPreferences(baseForSetting, Context.MODE_PRIVATE)
        chooseLang = resLang?.getString(keyLanguage, mLanguageCodeUa)!!
        LocaleHelper.setLocale(this, chooseLang)

        super.onCreate(savedInstanceState)
        bindingClass = ActivitySettingBinding.inflate(layoutInflater)



        // language selection after restart
        when(chooseLang) {
            mLanguageCodeEn->{bindingClass.ukraineLanguage.setBackgroundResource(R.color.less_blue)
                bindingClass.englishLanguage.setBackgroundResource(R.color.dark_blue)}
            mLanguageCodeUa->{ bindingClass.ukraineLanguage.setBackgroundResource(R.color.dark_blue)
                bindingClass.englishLanguage.setBackgroundResource(R.color.less_blue)}
        }

        // font size selection after restart
        chooseFont = resLang?.getString(keyFont, medium)!!
        when(chooseFont) {
            small ->{
                selectColorsFontSize(R.drawable.circle_button_dark_blue, R.drawable.circle_button,
                    R.drawable.circle_button)
                SetSizeFont(smallSize)
            }
            medium ->{
                selectColorsFontSize(R.drawable.circle_button, R.drawable.circle_button_dark_blue,
                    R.drawable.circle_button)
                SetSizeFont(mediumSize)
            }
            big ->{
                selectColorsFontSize(R.drawable.circle_button, R.drawable.circle_button,
                    R.drawable.circle_button_dark_blue)
                SetSizeFont(bigSize)
            }
        }

        // select sound after restart
        count = resLang?.getInt("count", 1)!!
        SoundButton(count!!)

        // saved checkBox
        bindingClass.fiveMinute.isChecked = resLang?.getBoolean(five, false)!!
        bindingClass.tenMinute.isChecked = resLang?.getBoolean(ten, false)!!
        bindingClass.fifteenMinute.isChecked = resLang?.getBoolean(fifteen, false)!!
        bindingClass.twelveMinute.isChecked = resLang?.getBoolean(twelve, false)!!

        setContentView(bindingClass.root)


        //set background
        findViewById<DrawerLayout>(R.id.drawerLayout).foreground.alpha=0

        //Toolbar
        val toolbar: Toolbar = bindingClass.toolbar
        toolbar.setTitle(R.string.toolbar_schedule)
        setSupportActionBar(toolbar)

        // change language
        bindingClass.ukraineLanguage.setOnClickListener(View.OnClickListener
        {
            LocaleHelper.setLocale(this, mLanguageCodeUa)
            chooseLang = mLanguageCodeUa
            recreate()

        })

        bindingClass.englishLanguage.setOnClickListener(View.OnClickListener
        {
            LocaleHelper.setLocale(this, mLanguageCodeEn)
            chooseLang = mLanguageCodeEn
            recreate()
        })

        // change Font
        bindingClass.buttonS.setOnClickListener(View.OnClickListener
        {
            chooseFont = small
            chooseSizeKoef = smallSize
            SetSizeFont(smallSize)
            recreate()
        })
        bindingClass.buttonM.setOnClickListener(View.OnClickListener
        {
            chooseFont = medium
            chooseSizeKoef = mediumSize
            SetSizeFont(mediumSize)
            recreate()

        })
        bindingClass.buttonB.setOnClickListener(View.OnClickListener
        {
            chooseFont = big
            chooseSizeKoef = bigSize
            SetSizeFont(bigSize)
            recreate()

        })
        bindingClass.soundButton.setOnClickListener(View.OnClickListener{
            count = resLang?.getInt("count", 1)!!
            when(count){
                0->{count=1}
                1->{count=2}
                2->{count=0
                    rememberCheckBox()
                }
            }
            SoundButton(count!!)
            intSaver("count", count!!)
        })




        //Navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer(this)

        //Read User Information
        val ReadUser = ReadFirebase()
        ReadUser.readFirebaseUser(bindingClass)
    }
    fun Alarmm(hour:IntArray, minute:IntArray, time:Int){
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        var hour1 = hour
        var minute1 = minute
        for (i in 0..(hour.size - 1)) {
            if (minute[i] - time < 0) {
                hour1[i] -= 1
                minute1[i] = 60 + (minute1[i] - time)
            } else {
                minute1[i] -= time
            }
            val materialTimePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour1[i])
                .setMinute(minute1[i])
                .setTitleText("Будильник задзвенить о: ")
                .build()
            val calendar = Calendar.getInstance()
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
            calendar[Calendar.MINUTE] = minute1[i]
            calendar[Calendar.HOUR_OF_DAY] = hour1[i]
            val alarmManager =
                getSystemService(ALARM_SERVICE) as AlarmManager
            val alarmClockInfo = AlarmManager.AlarmClockInfo(
                calendar.timeInMillis,
                alarmInfoPendingIntent
            )
            alarmManager.setAlarmClock(alarmClockInfo, alarmActionPendingIntent)
            Toast.makeText(
                this,
                "Нагадування встановлене на " + sdf.format(calendar.time),
                Toast.LENGTH_SHORT
            ).show()
        }


        // Якщо не працює будильник у android 10,
        //потрібно запитати дозвіл на показ вікон поверх інших програм
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"))
            startActivity(intent)
        }

    }


    private val alarmInfoPendingIntent: PendingIntent
        @SuppressLint("UnspecifiedImmutableFlag")
        get() {
            val alarmInfoIntent = Intent(this, SettingActivity::class.java)
            alarmInfoIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            return PendingIntent.getActivity(
                this,
                0,
                alarmInfoIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    private val alarmActionPendingIntent: PendingIntent
        @SuppressLint("UnspecifiedImmutableFlag")
        get() {
            val intent = Intent(this, AlarmActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }


    @SuppressLint("SimpleDateFormat")
    fun rememberCheckBox(){
        val fiveMin = bindingClass.fiveMinute.isChecked
        val tenMinute = bindingClass.tenMinute.isChecked
        val fifteenMinute = bindingClass.fifteenMinute.isChecked
        val twelveMinute = bindingClass.twelveMinute.isChecked

        if (fiveMin)
            Alarmm(hour, minute, time[0])
        if (tenMinute)
            Alarmm(hour, minute, time[1])
        if (fifteenMinute)
            Alarmm(hour, minute, time[2])
        if (twelveMinute)
            Alarmm(hour, minute, time[3])
    }


    fun SoundButton(count:Int){
        when(count){
            0->{bindingClass.offOn.setText(R.string.on)
                bindingClass.ImageMiddleTextView.setBackgroundResource(R.drawable.ic_sound_on)}
            1-> {bindingClass.offOn.setText(R.string.off)
                bindingClass.ImageMiddleTextView.setBackgroundResource(R.drawable.ic_sound_off)}
            2->{bindingClass.offOn.setText(R.string.vibro)
                bindingClass.ImageMiddleTextView.setBackgroundResource(R.drawable.ic_sound_vibro)}
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    fun changeB(view:View){
        // call checking the password
        CheckPassword(R.layout.activity_check_password, view)
    }

    // checking the password
    fun CheckPassword(activityScreen:Int, view:View) {

        val popupView = wayScreenDisplay(activityScreen, view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 255

        val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable = true
        // слухач кнопок
        val agree2 = popupView.findViewById(R.id.agreePassword) as Button
        val checkPassword = popupView.findViewById(R.id.checkPassword) as EditText

        popupWindow.setOnDismissListener {
            drawerLayout.foreground.alpha = 0
        }


        agree2.setOnClickListener(View.OnClickListener(){
            val user = Firebase.auth.currentUser
            result = true
            val uid = user!!.uid

            val referenceUser = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
            referenceUser.child(uid).get().addOnSuccessListener {
                if (it.exists()) {

                    val userName = it.child("password").value
                    if (userName.toString() == checkPassword.toString())
                        result = true

                    //Toast.makeText(this, "User information read...", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this, "User isn't in firebase!!!", Toast.LENGTH_SHORT).show()
                    result = true
                }
            }.addOnFailureListener {
                //Toast.makeText(this, "Failed read User ", Toast.LENGTH_SHORT).show()
                result = true
            }


            popupWindow.dismiss()
            if (result as Boolean)
            // call dialog where you can change the data
                EditInformation(R.layout.activity_edit_setting, view)
        })
    }

    // go to dialog where you can change the data
    fun EditInformation(activityScreen:Int, view:View){
        val popupView = wayScreenDisplay(activityScreen, view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)
        drawerLayout.foreground.alpha = 255
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable = true
        // слухач кнопок
        val agree = popupView.findViewById(R.id.agree) as Button

        popupWindow.setOnDismissListener {
            drawerLayout.foreground.alpha = 0
        }

        agree.setOnClickListener(View.OnClickListener(){
            drawerLayout.foreground.alpha = 0
            popupWindow.dismiss()

            val user = Firebase.auth.currentUser
            val uid = user!!.uid
            //не працює
            //val userID = firebaseAuth.uid

            val groupName = popupView.findViewById(R.id.editGroup) as EditText
            val password = popupView.findViewById(R.id.editPassword) as EditText
            val userName = popupView.findViewById(R.id.editLogin) as EditText

            val passwor = password.text.toString()

            val users = User(groupName.text.toString(), password.text.toString())

            val profileUpdates = userProfileChangeRequest {
                displayName = userName.text.toString()
            }
            user!!.updateProfile(profileUpdates)


            //не працює
            user!!.updateEmail("user@gmail.com")
            user!!.updatePassword(passwor)


            val reference = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")

            reference.child(uid!! ).setValue(users)

        })
    }


    fun wayScreenDisplay(activityScreen:Int, view:View): View {
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(activityScreen, null)
        return popupView
    }

    fun stringfSaver(key:String, value: String){
        val editor = resLang?.edit()
        editor?.putString(key, value)
        editor?.apply()
    }
    fun intSaver(key:String, value: Int){
        val editor = resLang?.edit()
        editor?.putInt(key, value)
        editor?.apply()
    }
    fun boolSaver(key:String, value: Boolean){
        val editor = resLang?.edit()
        editor?.putBoolean(key, value)
        editor?.apply()
    }


    private fun selectColorsFontSize(smallB : Int, mediumB : Int, bigB : Int)
    {
        bindingClass.buttonS.setBackgroundResource(smallB)
        bindingClass.buttonM.setBackgroundResource(mediumB)
        bindingClass.buttonB.setBackgroundResource(bigB)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        stringfSaver(keyLanguage, chooseLang)
        stringfSaver(keyFont, chooseFont)
        boolSaver(five, bindingClass.fiveMinute.isChecked)
        boolSaver(ten, bindingClass.tenMinute.isChecked)
        boolSaver(fifteen, bindingClass.fifteenMinute.isChecked)
        boolSaver(twelve, bindingClass.twelveMinute.isChecked)

    }



}