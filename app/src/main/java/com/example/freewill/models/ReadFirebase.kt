package com.example.freewill.models

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.ReviewActivity
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ReadFirebase {
    val FirebaseData=FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

    fun ReadSchedule(group: String?, day:String?,binding: FragmentScheduleBinding) {

        val referenceSchedule = FirebaseData.getReference("Shedule")
        referenceSchedule.child(group!!).child(day!!).get().addOnSuccessListener{
            if(it.exists())
            {
                val firstpara =it.child("firstpara").child("para").value
                val secondpara =it.child("secondpara").child("para").value
                val thirdpara =it.child("thirdpara").child("para").value
                val fourthpara =it.child("fourthpara").child("para").value
                val fifthpara =it.child("fifthpara").child("para").value

                val audut1 =it.child("firstpara").child("audutoria").value
                val audut2 =it.child("secondpara").child("audutoria").value
                val audut3 =it.child("thirdpara").child("audutoria").value
                val audut4 =it.child("fourthpara").child("audutoria").value
                val audut5 =it.child("fifthpara").child("audutoria").value

                binding.class1.setText(firstpara.toString())
                binding.class2.setText(secondpara.toString())
                binding.class3.setText(thirdpara.toString())
                binding.class4.setText(fourthpara.toString())
                binding.class5.setText(fifthpara.toString())

                binding.aud1.setText(audut1.toString())
                binding.aud2.setText(audut2.toString())
                binding.aud3.setText(audut3.toString())
                binding.aud4.setText(audut4.toString())
                binding.aud5.setText(audut5.toString())
                //Toast.makeText(activity, "Shedule read...", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //Toast.makeText(activity, "Shedule not read!!!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            //Toast.makeText(activity, "Failed read shedule ", Toast.LENGTH_SHORT).show()
        }

        val user = Firebase.auth.currentUser
        val uid = user!!.uid
        val referenceScheduleUser = FirebaseData.getReference("Users")
        referenceScheduleUser.child(uid!!).child("Shedule").child(day).get().addOnSuccessListener{
            if(it.exists())
            {
                val firstpara =it.child("firstpara").value
                val secondpara =it.child("secondpara").value
                val thirdpara =it.child("thirdpara").value
                val fourthpara =it.child("fourthpara").value
                val fifthpara =it.child("fifthpara").value

                if(firstpara.toString()!="") {
                    binding.class1.setText(firstpara.toString())
                }
                if(secondpara.toString()!="") {
                    binding.class2.setText(secondpara.toString())
                }
                if(thirdpara.toString()!="") {
                    binding.class3.setText(thirdpara.toString())
                }
                if(fourthpara.toString()!="") {
                    binding.class4.setText(fourthpara.toString())
                }
                if(fifthpara.toString()!="") {
                    binding.class5.setText(fifthpara.toString())
                }

                //Toast.makeText(activity, "Shedule read from User...", Toast.LENGTH_SHORT).show()
            }
            else
            {
                //Toast.makeText(activity, "Shedule not from user read!!!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            //Toast.makeText(activity, "Failed read shedule ", Toast.LENGTH_SHORT).show()
        }
    }

    fun UpdateShedule(monday: Day, ChangeDay: String ,activityClass: AppCompatActivity)
    {
        val user = Firebase.auth.currentUser
        val uid = user!!.uid

        val referenceShedule = FirebaseData.getReference("Users")

        //add user data to database
        referenceShedule.child(uid).child("Shedule").child(ChangeDay!!).setValue(monday)
            .addOnSuccessListener {
                Toast.makeText(activityClass, "Change of shedule add...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                Toast.makeText(activityClass, "Failed saving shedule due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun readGroupUser(onSuccess:(String)->Unit) {
        val user = Firebase.auth.currentUser
        val uid = user!!.uid
        var group:String=""
        val referenceUser = FirebaseData.getReference("Users")
        referenceUser.child(uid).child("groupName").get().addOnSuccessListener {
            group= when (it.exists()) {
                true -> it.value.toString()
                false -> ""
            }
            onSuccess(group)
        }
    }

    fun readFirebaseUser( binding: ActivitySettingBinding) {
        val user = Firebase.auth.currentUser

        user?.let {
            val email = user.email
            val name = user.displayName
            binding.editGmail.setText(email.toString())
            binding.editLogin.setText(name.toString())
            val uid = user.uid

            val referenceUser = FirebaseData.getReference("Users")
            referenceUser.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val group = it.child("groupName").value
                    binding.editGroup.setText(group.toString())
                } else {}
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
    }

    fun updateRating(fullName: String, department: String) { // TODO

    }

}