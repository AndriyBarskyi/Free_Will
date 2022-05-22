package com.example.freewill

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ReadFirebase
import com.google.android.material.navigation.NavigationView
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

open class SettingActivity : AppCompatActivity()
{
    val period = "period"
    val clockIndex = "index"
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
    val hours:IntArray= intArrayOf(8,10,11,13)
    val minutes:IntArray= intArrayOf(30,10,50,30)
    val timesTo:IntArray= intArrayOf(5,10,15,20)

    var chooseSizeKoef : Float? = null
    var count :Int?=null
    var resLang : SharedPreferences? = null
    lateinit var chooseFont : String
    lateinit var bindingClass: ActivitySettingBinding
    lateinit var chooseLang : String
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    fun SetSizeFont(size_coef: Float)
    {
        val configuration = resources.configuration
        configuration.fontScale = size_coef
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics.scaledDensity = configuration.fontScale * metrics.density
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
    fun setOption(){
        // Якщо не працює будильник у android,
        //потрібно запитати дозвіл на показ вікон поверх інших програм
        //
        //
        if (resLang?.getBoolean("setting", true) == true) {
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
            boolSaver("setting", false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("ResourceAsColor", "CutPasteId", "UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        setOption()
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
        toolbar.setTitle(R.string.toolbar_settings)
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


        bindingClass.forgotPasswordSetting.setOnClickListener{
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            finish()
        }

        bindingClass.fiveMinute.setOnClickListener(View.OnClickListener
        {
            selectCheckBox(bindingClass.fiveMinute.isChecked,
                false,false,false)
        })
        bindingClass.tenMinute.setOnClickListener(View.OnClickListener
        {
            selectCheckBox(false,
                bindingClass.tenMinute.isChecked,false,false)
        })
        bindingClass.fifteenMinute.setOnClickListener(View.OnClickListener
        {
            selectCheckBox(false,
                false, bindingClass.fifteenMinute.isChecked,false)
        })
        bindingClass.twelveMinute.setOnClickListener(View.OnClickListener
        {
            selectCheckBox(false,
                false,false, bindingClass.twelveMinute.isChecked)
        })



        bindingClass.soundButton.setOnClickListener(View.OnClickListener{
            count = resLang?.getInt("count", 1)!!
            when(count){
                0->{
                    count=1
                    selectCheckBox(false,false,false,false)
                    intSaver(clockIndex, 0)
                }
                1->{
                    count=2
                }
                2->{
                    count=0
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




    @RequiresApi(Build.VERSION_CODES.O)
    fun Alarmm(hour: Int, minute:Int){
        val materialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Нагадування")
            .build()

        val calendar = Calendar.getInstance()
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
        calendar[Calendar.MINUTE] = minute
        calendar[Calendar.HOUR_OF_DAY] = hour

        if (calendar.timeInMillis <= System.currentTimeMillis())
            calendar.add(Calendar.DAY_OF_YEAR, 1)

        val alarmManager =
            getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val alarmClockInfo = AlarmManager.AlarmClockInfo(
            calendar.timeInMillis,
            alarmInfoPendingIntent
        )
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        alarmManager.setAlarmClock(alarmClockInfo, alarmActionPendingIntent)
        Toast.makeText(
            this,
            "Нагадування встановлене о " + sdf.format(calendar.time),
            Toast.LENGTH_SHORT
        ).show()
    }

    private val alarmInfoPendingIntent: PendingIntent
        @SuppressLint("UnspecifiedImmutableFlag") get() {
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
        @SuppressLint("UnspecifiedImmutableFlag") get() {
            val intent = Intent(this, AlarmActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            return PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }



    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SimpleDateFormat")
    fun ActivateCheckBox(foundIndex : SharedPreferences): IntArray? {
        var index = foundIndex.getInt(clockIndex, 0)
        if (foundIndex?.getBoolean(five, false) == true)
        {
            val res = ResultTime(timesTo[0], index)
            return intArrayOf(res[0],res[1])
        }
        if (foundIndex?.getBoolean(ten, false) == true)
        {
            val res = ResultTime(timesTo[1],index)
            return intArrayOf(res[0],res[1])
        }
        if (foundIndex?.getBoolean(fifteen, false) == true)
        {
            val res = ResultTime(timesTo[2],index)
            return intArrayOf(res[0],res[1])
        }
        if (foundIndex?.getBoolean(twelve, false) == true)
        {
            val res = ResultTime(timesTo[3],index)
            return intArrayOf(res[0],res[1])
        }

        return null
    }

    fun ResultTime(time: Int, i: Int): IntArray {
        val tempHours: IntArray = hours.copyOf()
        val tempMinutes: IntArray = minutes.copyOf()
        if (tempMinutes[i] - time >= 0) {
            tempMinutes[i] -= time
        } else {
            tempMinutes[i] = 60 + tempMinutes[i] - time
            tempHours[i] -= 1
        }
        return intArrayOf(tempHours[i], tempMinutes[i]);
    }

    fun SoundButton(count:Int){
        when(count){
            0->{bindingClass.offOn.setText(R.string.on)
                bindingClass.soundButton.setBackgroundResource(R.drawable.ic_sound_on)}
            1-> {bindingClass.offOn.setText(R.string.off)
                bindingClass.soundButton.setBackgroundResource(R.drawable.ic_sound_off)}
            2->{bindingClass.offOn.setText(R.string.vibro)
                bindingClass.soundButton.setBackgroundResource(R.drawable.ic_sound_vibro)}
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    fun changeB(view: View) {
        // call checking the password
        EditInformation(R.layout.activity_edit_setting, view)
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

            val groupName = popupView.findViewById(R.id.editGroup)as EditText
            val userName = popupView.findViewById(R.id.editLogin) as EditText
            val profileUpdates = userProfileChangeRequest {
                displayName = userName.text.toString().trim()
            }
            user!!.updateProfile(profileUpdates)
            val reference = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")

            reference.child(uid!! ).child("groupName").setValue(groupName.text.toString().trim())

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
    fun intSaver(key: String, value: Int){
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
    @RequiresApi(Build.VERSION_CODES.O)
    private fun selectCheckBox(
        fiveBox: Boolean, tenBox: Boolean,
        fifteenBox: Boolean, twelveBox: Boolean,
    )
    {
        if(bindingClass.offOn.text == getString(R.string.off)){
            bindingClass.fiveMinute.isChecked = false
            bindingClass.tenMinute.isChecked = false
            bindingClass.fifteenMinute.isChecked = false
            bindingClass.twelveMinute.isChecked = false
        }
        else{
            bindingClass.fiveMinute.isChecked = fiveBox
            bindingClass.tenMinute.isChecked = tenBox
            bindingClass.fifteenMinute.isChecked = fifteenBox
            bindingClass.twelveMinute.isChecked = twelveBox
        }
        boolSaver(five, bindingClass.fiveMinute.isChecked)
        boolSaver(ten, bindingClass.tenMinute.isChecked)
        boolSaver(fifteen, bindingClass.fifteenMinute.isChecked)
        boolSaver(twelve, bindingClass.twelveMinute.isChecked)

        val alert = ActivateCheckBox(resLang!!)
        if (alert != null)
            Alarmm(alert[0],alert[1])

        onResume()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        if(resLang?.getBoolean(period,false)==true){
            boolSaver(period,false)
            val alert = ActivateCheckBox(resLang!!)
            if (alert !=null)
                Alarmm(alert[0], alert[1])
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        stringfSaver(keyLanguage, chooseLang)
        stringfSaver(keyFont, chooseFont)
    }





}