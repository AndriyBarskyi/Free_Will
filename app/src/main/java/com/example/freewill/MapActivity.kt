package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityMapBinding
import com.example.freewill.databinding.ActivitySearchPopupBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MapActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var mapBinding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: NavigationView = findViewById(R.id.navView)
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> startActivity(Intent(this, ScheduleActivity::class.java))

                R.id.nav_map -> startActivity(Intent(this, MapActivity::class.java))

                R.id.nav_settings ->
                    Toast.makeText(applicationContext, "No Settings yet", Toast.LENGTH_SHORT).show()

            }
            true
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    fun onClickOpenPopUp(view:View) {
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView: View = inflater.inflate(R.layout.activity_search_popup, null)

        //Specify the length and width through constants
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT

        //Make Inactive Items Outside Of PopupWindow
        val focusable = true

        //Create a window with our parameters
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)



        //Handler for clicking on the inactive zone of the window
        popupView.setOnTouchListener { v, event -> //Close the window when clicked
            popupWindow.dismiss()
            true
        }
    }

}