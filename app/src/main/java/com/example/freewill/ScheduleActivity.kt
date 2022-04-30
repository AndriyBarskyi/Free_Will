package com.example.freewill

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_schedule)
        setSupportActionBar(toolbar)

        //firebaseAuth = FirebaseAuth.getInstance()
        //val user = firebaseAuth.currentUser
        //binding.class1.setText(user!!.toString())
        //if(user!!.isNotEmpty())
        //{
            //readGroup(user)
        //}
        var userGrop="pmi-25"


        binding.ChangeBtn1.setOnClickListener {
            startActivity(Intent(this, EditScheduleActivity::class.java))
        }
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")


        //readSchedule()
        //reference of table 'Shedule'
//        reference = database.getReference("monday")
//
//
//        val postListener = object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    // This method is called once with the initial value and again
//                    // whenever data at this location is updated.
//                    val value = snapshot.getValue<Day>()
//                    Log.d(TAG, "Value is: " + value)
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    Log.w(TAG, "Failed to read value.", error.toException())
//                }
//            }
//        reference.addValueEventListener(postListener)






//      Navigation bar
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        toggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.navView)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> {
                    val i = Intent(this, ScheduleActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_map -> {
                    val i = Intent(this, MapActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_settings -> {
                    val i = Intent(this, SettingActivity::class.java)
                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }
            true
        }


    }




    private fun readSchedule() {
        TODO("Not yet implemented")
    }

    private fun readGroup(user: String?) {

        val referenceUser = database.getReference("Users")
        referenceUser.child(user!!).get().addOnSuccessListener{
            if(it.exists())
            {
                val group =it.child("groupName").value
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}