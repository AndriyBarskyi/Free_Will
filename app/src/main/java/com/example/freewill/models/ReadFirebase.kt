package com.example.freewill.models

import android.content.Context
import android.widget.Toast
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ReadFirebase {


    fun readGroupUser(): String {
        val user = Firebase.auth.currentUser
        var group:String =""
        user?.let {
            val uid = user.uid

            val referenceUser = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
            referenceUser.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    group = it.child("groupName").value.toString()
                } else {}
            }.addOnFailureListener {}
        }
        return group
    }

    fun readFirebaseUser( binding: ActivitySettingBinding) {
        val user = Firebase.auth.currentUser

        user?.let {
            val email = user.email
            binding.editGmail.setText(email.toString())
            val uid = user.uid

            val referenceUser = FirebaseDatabase
                .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Users")
            referenceUser.child(uid).get().addOnSuccessListener {
                if (it.exists()) {
                    val group = it.child("groupName").value
                    val userName = it.child("userName").value
                    //val email = it.child("email").value

                    binding.editGroup.setText(group.toString())
                    binding.editLogin.setText(userName.toString())
                    //bindingClass.editGmail.setText(email.toString())

                    //Toast.makeText(activity, "User information read...", Toast.LENGTH_SHORT).show()
                } else {
                    //Toast.makeText(this, "User isn't in firebase!!!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                //Toast.makeText(this, "Failed read User ", Toast.LENGTH_SHORT).show()

            }
        }
    }
}