package com.example.freewill

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ScheduleAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var database: FirebaseDatabase
    private lateinit var referenceSchedule: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var adapter: ScheduleAdapter
    private lateinit var viewPager: ViewPager2

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar: Calendar = Calendar.getInstance();
        val simpleDateFormat: SimpleDateFormat= SimpleDateFormat("EEEE dd.MM.yyyy")
        val dataTime : String =simpleDateFormat.format(calendar.getTime());
        binding.textView2.setText(dataTime);

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_schedule)
        setSupportActionBar(toolbar)

        val fragments: ArrayList<Fragment> = arrayListOf(
            ScheduleFragment(),
            ScheduleFragment2()
        )
        adapter = ScheduleAdapter(fragments, this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter=adapter

        //firebaseAuth = FirebaseAuth.getInstance()
        //val user = firebaseAuth.currentUser
        //binding.class1.setText(user!!.toString())
        //if(user!!.isNotEmpty())
        //{
            //readGroup(user)
        //}
        //var userGrop="pmi_24"
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






//      Navigation bar
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer(this)
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