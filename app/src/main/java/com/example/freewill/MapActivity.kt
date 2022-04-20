package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityMapBinding
import com.example.freewill.databinding.ActivitySearchPopupBinding
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

        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        mapBinding.findDirectionButton.setOnClickListener {
            val intent = Intent(this, SearchPopUpActivity::class.java)
            startActivity(intent)
        }

    }

}