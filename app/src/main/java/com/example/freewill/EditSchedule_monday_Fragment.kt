package com.example.freewill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.freewill.R
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.databinding.FragmentEditScheduleMondayBinding
import com.example.freewill.models.Day
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.content.Intent


class EditSchedule_monday_Fragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentEditScheduleMondayBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_edit_schedule_monday_, container, false)
        binding = FragmentEditScheduleMondayBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditSchedule.setOnClickListener{
            val firstpara = binding.classEditText1.text.toString().trim()
            val secondpara = binding.classEditText2.text.toString().trim()
            val thirdpara = binding.classEditText3.text.toString().trim()
            val fourthpara = binding.classEditText4.text.toString().trim()
            val fifthpara = binding.classEditText5.text.toString().trim()

            val ChangeDay = Day(firstpara, secondpara, thirdpara, fourthpara, fifthpara)
            updateShedule(ChangeDay)
            //startActivity(Intent(this, ScheduleActivity::class.java))
        }

    }



    companion object {
        @JvmStatic
        fun newInstance() = EditSchedule_monday_Fragment()
    }


    private fun updateShedule(monday: Day)
    {

        val nameOfGroup = "pmi_25"
        //val ChangeDay = binding.monday.text.toString().trim()
        val ChangeDay ="monday"

        //init reference of database
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

        //reference of table 'Shedule'
        reference = database.getReference("Shedule")

        //add user data to database
        reference.child(nameOfGroup).child(ChangeDay!!).setValue(monday)
            .addOnSuccessListener {
                //data successfully added to database
                //progressDialog.dismiss()
                //Toast.makeText(this, "Shedule add...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                //failed setting data in database
                //progressDialog.dismiss()
                //Toast.makeText(this, "Failed saving shedule due to ${e.message}", Toast.LENGTH_SHORT).show()

            }

    }
}