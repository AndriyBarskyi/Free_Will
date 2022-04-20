package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.models.Day
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditScheduleBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //firebaseAuth = FirebaseAuth.getInstance()
        //val  user = firebaseAuth.uid.
        //startActivity(Intent(this, ScheduleActivity::class.java))


        //OpenFrag(EditSchedule_monday_Fragment.newInstance(), R.id.FrameLayout)
        binding.monday.setOnClickListener{
            OpenFrag(EditSchedule_monday_Fragment.newInstance(), R.id.FrameLayout)
        }
        binding.tuesday.setOnClickListener{
            OpenFrag(EditSchedule_tuesday_Fragment.newInstance(), R.id.FrameLayout)
        }
        

    
    }
    private fun OpenFrag(f:Fragment, idHolder:Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder,f)
            .commit()
    }

}