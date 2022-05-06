package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.freewill.databinding.ActivityLetterEntranceBinding
import com.example.freewill.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LetterEntranceActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private lateinit var binding: ActivityLetterEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.confirmButton.setOnClickListener {
            val user = firebaseAuth.currentUser
            user!!.sendEmailVerification()
                .addOnSuccessListener {
                    Toast.makeText(this, "Verification link was sent", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to send a verification link", Toast.LENGTH_SHORT).show()
                }
        }
//        Handler(Looper.getMainLooper()).postDelayed(object: Runnable{
//            override fun run(){
//                isUserVerified()
//            }
//        }, 200)
//        binding.confirmButton2.setOnClickListener {
//            isUserVerified()
//        }
    }

//    private fun isUserVerified(){
//        firebaseAuth = FirebaseAuth.getInstance()
//        val user = firebaseAuth.currentUser
//        if(user!!.isEmailVerified){
//            startActivity(Intent(this, ScheduleActivity::class.java))
//            finish()
//        }
//        else{
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
//        }
//    }

}