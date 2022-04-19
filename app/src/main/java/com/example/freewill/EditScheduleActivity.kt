package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.models.Day
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditScheduleBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEditSchedule.setOnClickListener{
            val firstpara = binding.classEditText1.text.toString().trim()
            val secondpara = binding.classEditText2.text.toString().trim()
            val thirdpara = binding.classEditText3.text.toString().trim()
            val fourthpara = binding.classEditText4.text.toString().trim()
            val fifthpara = binding.classEditText5.text.toString().trim()

            val ChangeDay = Day(firstpara, secondpara, thirdpara, fourthpara, fifthpara)
            updateShedule(ChangeDay)
            startActivity(Intent(this, ScheduleActivity::class.java))
        }
    }

    private fun updateShedule(monday:Day)
    {

        val nameOfGroup = "pmi_25"
        val ChangeDay = binding.monday.text.toString().trim()


        //init reference of database
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

        //reference of table 'Shedule'
        reference = database.getReference("Shedule")

        //add user data to database
        reference.child(nameOfGroup).child(ChangeDay!!).setValue(monday)
            .addOnSuccessListener {
                //data successfully added to database
                //progressDialog.dismiss()
                Toast.makeText(this, "Shedule add...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                //failed setting data in database
                //progressDialog.dismiss()
                Toast.makeText(this, "Failed saving shedule due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }
}