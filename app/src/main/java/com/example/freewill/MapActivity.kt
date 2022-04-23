package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityMapBinding
import com.google.android.material.navigation.NavigationView


class MapActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var mapBinding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 0
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: NavigationView = findViewById(R.id.navView)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> startActivity(Intent(this, ScheduleActivity::class.java))

                R.id.nav_map -> startActivity(Intent(this, MapActivity::class.java))

                R.id.nav_settings -> startActivity(Intent(this, SettingActivity::class.java))
            }
            true
        }
    }

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    fun onClickOpenPopUp(view:View) {
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 255
        val popupView: View = inflater.inflate(R.layout.activity_search_popup, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener {
            drawerLayout.foreground.alpha = 0
        }
    }

}