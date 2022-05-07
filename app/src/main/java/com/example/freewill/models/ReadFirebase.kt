package com.example.freewill.models

import android.content.Context
import android.widget.Toast
import com.example.freewill.databinding.ActivitySettingBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ReadFirebase {

    var group: String ="0"
    fun readGroupUser(): String {
        val user = Firebase.auth.currentUser
        val uid = user!!.uid

        val referenceUser = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Users")
        referenceUser.child(uid).get().addOnSuccessListener {
            if (it.exists()) {
                group = "4"
                group = it.child("groupName").value.toString()
            } else {
                group = "2"
            }
        }.addOnFailureListener { group = "3" }


        return group
    }

    fun readFirebaseUser( binding: ActivitySettingBinding) {
        val user = Firebase.auth.currentUser

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
                } else {}
            }.addOnFailureListener {}
        }
    }
}