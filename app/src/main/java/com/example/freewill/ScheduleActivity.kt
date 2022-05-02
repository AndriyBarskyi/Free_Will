package com.example.freewill

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.models.NavigationClass
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var database: FirebaseDatabase
    private lateinit var referenceSchedule: DatabaseReference
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
        var userGrop="pmi_24"
        //readSchedule(userGrop)

        binding.ChangeBtn1.setOnClickListener {
            startActivity(Intent(this, EditScheduleActivity::class.java))
        }
        //database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")



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

//          Navigation bar
        createNavigationMenu()
    }

    fun createNavigationMenu(){
        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_schedule)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        val navView: NavigationView = findViewById(R.id.navView)

        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer()
    }






//    private fun readSchedule(user: String?) {
//
//        val referenceSchedule = FirebaseDatabase
//            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
//            .getReference("Shedule")
//        referenceSchedule.child(user!!).get().addOnSuccessListener{
//            if(it.exists())
//            {
//                val firstpara =it.child("firstpara").value
//                val secondpara =it.child("secondpara").value
//                val thirdpara =it.child("thirdpara").value
//                val fourthpara =it.child("fourthpara").value
//                val fifthpara =it.child("fifthpara").value
//
//                binding.class1.setText(firstpara.toString())
//                binding.class2.setText(secondpara.toString())
//                binding.class3.setText(thirdpara.toString())
//                binding.class4.setText(fourthpara.toString())
//                binding.class5.setText(fifthpara.toString())
//                Toast.makeText(this, "Shedule read...", Toast.LENGTH_SHORT).show()
//            }
//            else
//            {
//                Toast.makeText(this, "Shedule not read!!!", Toast.LENGTH_SHORT).show()
//            }
//        }.addOnFailureListener {
//            Toast.makeText(this, "Failed read shedule ", Toast.LENGTH_SHORT).show()
//
//        }
//
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}