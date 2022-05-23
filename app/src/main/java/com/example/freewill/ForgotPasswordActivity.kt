package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.freewill.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //resetButton is clicked
        binding.resetPasswordButton.setOnClickListener {
            resetPassword()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.backButton.setOnClickListener{
            super.onBackPressed()
        }
    }

    //reset user password with Firebase
    private fun resetPassword(){
        val emailText = binding.emailEditText.text.toString().trim()

        if(emailText.isEmpty()){
            binding.emailEditText.error = "Email is required"
            binding.emailEditText.requestFocus()
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()){
            //invalid email
            binding.emailEditText.error = "Invalid email format"
            binding.emailEditText.requestFocus()
        }
        firebaseAuth.sendPasswordResetEmail(emailText).addOnCompleteListener{
            if(it.isSuccessful){
                Toast.makeText(this, "Check your email to reset your password", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Try again! Something wrong happened!", Toast.LENGTH_LONG).show()
            }
        }
    }
}