package com.example.freewill

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.Day
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class EditScheduleActivity : AppCompatActivity() ,  View.OnClickListener
    {
            private lateinit var binding: ActivityEditScheduleBinding
            private lateinit var database: FirebaseDatabase
            private lateinit var reference: DatabaseReference
    //        val listClic = mutableListOf<TextView>(binding.monday,binding.tuesday,binding.wednesday,
    //            binding.thursday,binding.friday)

            private val dataModel: DataModel by viewModels()

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityEditScheduleBinding.inflate(layoutInflater)
                setContentView(binding.root)

                binding.backSched.setOnClickListener {
                    startActivity(Intent(this, ScheduleActivity::class.java))
                }

                binding.monday.setOnClickListener(this)
                binding.tuesday.setOnClickListener(this)
                binding.wednesday.setOnClickListener(this)
                binding.thursday.setOnClickListener(this)
                binding.friday.setOnClickListener(this)

            }

            private fun OpenFrag(f:Fragment, idHolder:Int) {
                supportFragmentManager
                    .beginTransaction()
                    .replace(idHolder,f)
                    .commit()
            }


            private fun updateShedule(monday: Day, ChangeDay: String)
            {
                val nameOfGroup = "pmi_25"
                //val ChangeDay="вівторок"
                database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                reference = database.getReference("Shedule")

                //add user data to database
                reference.child(nameOfGroup).child(ChangeDay!!).setValue(monday)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Shedule add...", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{ e ->
                        Toast.makeText(this, "Failed saving shedule due to ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }

        override fun onClick(v: View?) {
            when (v?.getId()) {

                R.id.monday , R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday ->{
                    v.setBackgroundColor(Color.BLACK)
                    OpenFrag(EditSchedule_Fragment.newInstance(), R.id.FramLayout)
//                    val _textId: TextView = findViewById(v.id)
//                    val text= _textId.text.toString()
                    dataModel.message.observe(this, {

                        updateShedule(it, v.id.toString())
                    })
                }
            }
        }
    }




