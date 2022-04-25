package com.example.freewill

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.models.Day
import com.example.freewill.models.User
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

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







//      Navigashion bar
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