package com.example.freewill


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.databinding.ActivitySettingBinding
import com.google.android.material.navigation.NavigationView


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
    val smallSize = 0.50f
    val mediumSize = 1.0f
    val bigSize = 1.6f

    var chooseSizeKoef : Float? = null
    var check :Boolean?=null
    lateinit var chooseFont : String
    lateinit var bindingClass: ActivitySettingBinding
    lateinit var chooseLang : String
    var resLang : SharedPreferences? = null
    private lateinit var binding: ActivityScheduleBinding
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

        //Navigation drawer
        NavigationDrawer()

    }



    fun saveLanguageAndFont(resLanguage:String, resFont:String)
    {
        val editor = resLang?.edit()
        editor?.putString(keyLanguage, resLanguage)
        editor?.putString(keyFont, resFont)
        editor?.apply()

    }


    fun selectColorsFontSize(smallB : Int, mediumB : Int, bigB : Int)
    {
        bindingClass.buttonS.setBackgroundResource(smallB)
        bindingClass.buttonM.setBackgroundResource(mediumB)
        bindingClass.buttonB.setBackgroundResource(bigB)
    }



    fun NavigationDrawer()
    {
        drawerLayout = findViewById(R.id.drawerLayout)

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = bindingClass.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_settings)
        setSupportActionBar(toolbar)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.navView)
        navView.itemIconTintList = null

        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> {
                    val i = Intent(this, ScheduleActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_map -> {
                    val i = Intent(this, MapActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_settings -> {
                    val i = Intent(this, SettingActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }
            true
        }

    }


    override fun onDestroy()
    {
        super.onDestroy()
        saveLanguageAndFont(chooseLang, chooseFont)

    }

}




