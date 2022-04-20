package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.databinding.ActivityScheduleBinding
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ChangeBtn1.setOnClickListener {
            startActivity(Intent(this, EditScheduleActivity::class.java))
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}