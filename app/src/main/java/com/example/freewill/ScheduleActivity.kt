package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class ScheduleActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: NavigationView = findViewById(R.id.navView)
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
//                R.id.nav_schedule ->
//                    Toast.makeText(applicationContext, "Schedule is clicked", Toast.LENGTH_SHORT).show()
//
//                R.id.nav_map ->
//                    Toast.makeText(applicationContext, "Map is clicked", Toast.LENGTH_SHORT).show()
//
//                R.id.nav_settings ->
//                    Toast.makeText(applicationContext, "Settings is clicked", Toast.LENGTH_SHORT).show()

                R.id.nav_schedule -> startActivity(Intent(this, ScheduleActivity::class.java))

                R.id.nav_map -> startActivity(Intent(this, MapActivity::class.java))

                R.id.nav_settings ->
                    Toast.makeText(applicationContext, "Settings is clicked", Toast.LENGTH_SHORT).show()

            }
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}