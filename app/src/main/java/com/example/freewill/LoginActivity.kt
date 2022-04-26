package com.example.freewill

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.freewill.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityLoginBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //ProgressDialog
    private lateinit var progressDialog: ProgressDialog

    //Firebase Authorization
    private lateinit var firebaseAuth: FirebaseAuth

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure actionBar
        actionBar = supportActionBar!!
        actionBar.title = "Login"

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth = FirebaseAuth.getInstance()

        //if no account open SingUpActivity
        binding.noAccountText.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //if login successfully open main app
        binding.loginButton.setOnClickListener {
            validateUser()
        }

        binding.loginWithoutPasswordButton.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }
    }

    private fun validateUser(){
        email = binding.emailEditText.text.toString()
        password = binding.passwordEditText.text.toString().trim()

        //check whether user's data is correct
        if(TextUtils.isEmpty(email))
        {
            binding.emailEditText.error = "Please enter your nickname"
        }
        else if(TextUtils.isEmpty(password))
        {
            binding.passwordEditText.error = "Please enter your password"
        }
        else
        {
            loginToFirebase()
        }
    }

    private fun loginToFirebase(){
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                //user successfully signed in
                progressDialog.dismiss()

                val firebaseUser = firebaseAuth.currentUser
                val email = firebaseUser!!.email
                Toast.makeText(this, "Logged as $email", Toast.LENGTH_SHORT).show()

                //move to main app activity
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
            .addOnFailureListener{ e->
                //user not signed in
                progressDialog.dismiss()
                Toast.makeText(this, "Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun checkUser()
    {
        val firebaseAuth = firebaseAuth.currentUser
        if(firebaseAuth != null)
        {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

    }
}