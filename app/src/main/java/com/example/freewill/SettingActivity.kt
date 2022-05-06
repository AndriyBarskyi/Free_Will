package com.example.freewill


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ReadFirebase
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


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


    @SuppressLint("ResourceAsColor")
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
            intSaver("count", count!!)
            when(count){
                0->{bindingClass.offOn.setText(R.string.on)
                    bindingClass.soundButton.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_sound_on))
                    count=1}
                1-> {
                    bindingClass.offOn.setText(R.string.off)
                    bindingClass.soundButton.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_sound_off))
                    count = 2
                }
                2->{bindingClass.offOn.setText(R.string.vibro)
                    bindingClass.soundButton.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_sound_vibro))
                    count=0}
            }
            intSaver("count", count!!)
        })



        //Navigation drawer
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer()

        //Read User Information
        val ReadUser = ReadFirebase()
        ReadUser.readFirebaseUser(bindingClass)
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    fun changeB(view:View){
        // call checking the password
        CheckPassword(R.layout.activity_check_password, view)
    }

    // checking the password
    fun CheckPassword(activityScreen:Int, view:View): Boolean? {

        val popupView = wayScreenDisplay(activityScreen, view)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 255

        val popupWindow = PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT, true)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable = true
        // слухач кнопок
        val agree2 = popupView.findViewById(R.id.agreePassword) as Button

        popupWindow.setOnDismissListener {
            drawerLayout.foreground.alpha = 0
        }

        result = false
        agree2.setOnClickListener(View.OnClickListener(){
            result = true
            popupWindow.dismiss()
            if(result as Boolean)
                // call dialog where you can change the data
                EditInformation(R.layout.activity_edit_setting, view)
        })
        val uuu = result
        return uuu
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
        })
    }

    fun wayScreenDisplay(activityScreen:Int, view:View): View {
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(activityScreen, null)
        return popupView
    }



    fun saveLanguageAndFont(resLanguage:String, resFont:String)
    {
        val editor = resLang?.edit()
        editor?.putString(keyLanguage, resLanguage)
        editor?.putString(keyFont, resFont)
        editor?.apply()

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

    fun selectColorsFontSize(smallB : Int, mediumB : Int, bigB : Int)
    {
        bindingClass.buttonS.setBackgroundResource(smallB)
        bindingClass.buttonM.setBackgroundResource(mediumB)
        bindingClass.buttonB.setBackgroundResource(bigB)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        saveLanguageAndFont(chooseLang, chooseFont)
        stringfSaver(keyLanguage, chooseLang)
        stringfSaver(keyFont, chooseFont)

    }



}




