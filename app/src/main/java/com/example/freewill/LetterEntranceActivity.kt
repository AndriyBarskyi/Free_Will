package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.freewill.databinding.ActivityLetterEntranceBinding
import com.google.firebase.auth.FirebaseAuth

class LetterEntranceActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: ActivityLetterEntranceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLetterEntranceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.confirmButton.setOnClickListener {
            val user = firebaseAuth.currentUser

            //sent an email verification to the current user
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
    }
}