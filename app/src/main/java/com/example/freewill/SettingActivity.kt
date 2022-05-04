package com.example.freewill


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.models.NavigationClass
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
        configuration.fontScale = size_coef


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


        bindingClass.soundButton.setOnClickListener(View.OnClickListener{
            count = resLang?.getInt("count", 1)!!
            intSaver("count", count!!)
            when(count){
                0->{bindingClass.offOn.text=R.string.on.toString()
                count=1}
                1->{bindingClass.offOn.text=R.string.off.toString()
                    count=2}
                2->{bindingClass.offOn.text=R.string.vibro.toString()
                    count=0}
            }
        })
        // change language
        selectLanguage()
        // change Font
        selectFont()
        //Navigation drawer
        createNavigationMenu()
    }

    fun selectLanguage(){
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
    }

    fun selectFont(){
        bindingClass.buttonS.setOnClickListener(View.OnClickListener
        {
            chooseFont = small
            chooseSizeKoef = smallSize
            SetSizeFont(smallSize)
            recreate()
        })
        bindingClass.buttonM.setOnClickListener(View.OnClickListener{
            chooseFont = medium
            chooseSizeKoef = mediumSize
            SetSizeFont(mediumSize)
            recreate()

        })
        bindingClass.buttonB.setOnClickListener(View.OnClickListener{
            chooseFont = big
            chooseSizeKoef = bigSize
            SetSizeFont(bigSize)
            recreate()
        })
    }

    fun stringSaver(key:String, value: String)
    {
        val editor = resLang?.edit()
        editor?.putString(key, value)
        editor?.apply()
        //Navigation drawer
        createNavigationMenu()
        //Read User Information
        readFirebaseUser()
    }

    fun readFirebaseUser() {
        val user = Firebase.auth.currentUser

        user?.let {
            val email = user.email
            bindingClass.editGmail.setText(email.toString())
            val uid = user.uid

            val referenceSchedule = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
            referenceSchedule.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val group = it.child("groupName").value
                    val userName = it.child("userName").value
                    //val email = it.child("email").value

                    bindingClass.editGroup.setText(group.toString())
                    bindingClass.editLogin.setText(userName.toString())
                    //bindingClass.editGmail.setText(email.toString())

                    Toast.makeText(this, "User information read...", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "User isn't in firebase!!!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed read User ", Toast.LENGTH_SHORT).show()

            }
        }
    }


    fun intSaver(key:String, value: Int)
    {
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



    fun createNavigationMenu()
    {
        drawerLayout = findViewById(R.id.drawerLayout)

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = bindingClass.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_settings)
        setSupportActionBar(toolbar)
//        actionBar?.setDisplayHomeAsUpEnabled(true)
//        actionBar?.setDisplayShowHomeEnabled(true)

        //      Navigation bar
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        val navView: NavigationView = findViewById(R.id.navView)

        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer()

    }


    override fun onDestroy()
    {
        super.onDestroy()
        //saveLanguageAndFont(chooseLang, chooseFont)
        stringSaver(keyLanguage, chooseLang)
        stringSaver(keyFont, chooseFont)

    }

}




