package com.example.freewill.models

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.ReviewActivity
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ReadFirebase {
    private val user = Firebase.auth.currentUser

    fun ReadSchedule(
        user: String?,
        activityClass: AppCompatActivity,
        binding: FragmentScheduleBinding
    ) {

        val referenceShedule = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Shedule")
        referenceShedule.child(user!!).child("Понеділок").get().addOnSuccessListener {
            if (it.exists()) {
                val firstpara = it.child("firstpara").value
                val secondpara = it.child("secondpara").value
                val thirdpara = it.child("thirdpara").value
                val fourthpara = it.child("fourthpara").value
                val fifthpara = it.child("fifthpara").value

                binding.class1.setText(firstpara.toString())
                binding.class2.setText(secondpara.toString())
                binding.class3.setText(thirdpara.toString())
                binding.class4.setText(fourthpara.toString())
                binding.class5.setText(fifthpara.toString())
                Toast.makeText(activityClass, "Shedule read...", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activityClass, "Shedule not read!!!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(activityClass, "Failed read shedule ", Toast.LENGTH_SHORT).show()
        }
    }

    fun UpdateShedule(monday: Day, ChangeDay: String, activityClass: AppCompatActivity) {
        val uid = user!!.uid

        val referenceShedule = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")

        //add user data to database
        referenceShedule.child(uid).child("Shedule").child(ChangeDay!!).setValue(monday)
            .addOnSuccessListener {
                Toast.makeText(activityClass, "Shedule add...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    activityClass,
                    "Failed saving shedule due to ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun readGroupUser(onSuccess: (String) -> Unit) {
        val uid = user!!.uid
        var group: String = ""
        val referenceUser = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")
        referenceUser.child(uid).child("groupName").get().addOnSuccessListener {
            group = when (it.exists()) {
                true -> it.value.toString()
                false -> ""
            }
            onSuccess(group)
        }
    }

    fun readFirebaseUser(binding: ActivitySettingBinding) {

        user?.let {
            val email = user.email
            val name = user.displayName
            binding.editGmail.setText(email.toString())
            binding.editLogin.setText(name.toString())
            val uid = user.uid

            val referenceUser = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
            referenceUser.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val group = it.child("groupName").value
                    binding.editGroup.setText(group.toString())
                } else {
                }
            }.addOnFailureListener {}
        }
    }

    fun readTeacher(fullName: String) { // TODO
        if (fullName.isNotEmpty()) {
            val referenceTeacher =
                FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Teachers")

        }
    }

    fun addReview(fullName: String, review: String, activityClass: AppCompatActivity) {
        if (fullName.isNotEmpty() && review.isNotEmpty()) {
            val referenceTeacher =
                FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Teachers")
            referenceTeacher.child(fullName).child("review").setValue(review).addOnSuccessListener {
                Toast.makeText(activityClass, "Review add...", Toast.LENGTH_SHORT).show()
            }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        activityClass,
                        "Failed saving review due to ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    fun addRating(fullName: String, department: String) { // TODO
        val referenceTeacher =
            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Teachers")
        val teacher: Teacher = Teacher(fullName, department)
        referenceTeacher.child(fullName)
    }

    fun updateRating(fullName: String, department: String) { // TODO

    }

}