package com.example.freewill

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.freewill.databinding.ActivitySignUpBinding
import com.example.freewill.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //ProgressBar
    private lateinit var progressDialog: ProgressDialog

    //Firebase Authorization
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private var userName = ""
    private var group = ""
    private var corporateEmail = ""
    private var password = ""

    private var user: User = User(userName, group, corporateEmail, password)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Please wait")
        progressDialog.setMessage("Logging In...")
        progressDialog.setCanceledOnTouchOutside(false)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()


        //sign up button event
        binding.signUpButton.setOnClickListener{
            validateUser()
        }
    }

    private fun validateUser(){
        val _user = binding.userNameEditText.text.toString().trim()
        val _groupName = binding.groupNameEditText.text.toString().trim()
        val _email = binding.emailEditText.text.toString().trim()
        val _password = binding.passwordEditText.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(_email).matches()){
            //invalid email
            binding.emailEditText.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(_password)){
            //password is not entered
            binding.passwordEditText.error = "Please enter the password"
        }
        else if(_password.length < 4){
            binding.passwordEditText.error = "Password must at least 4 characters long"
        }
        else if(TextUtils.isEmpty(_user)){
            binding.userNameEditText.error = "Please enter your nickname"
        }
        else if(TextUtils.isEmpty(_groupName)){
            binding.groupNameEditText.error = "Please enter your group"
        }
        else
        {
            //everything is correct
            firebaseAuth.createUserWithEmailAndPassword(_email, _password)
                .addOnCompleteListener(SignUpActivity()) {task->

                    if(task.isSuccessful){
                        updateUserInfo()
                        Toast.makeText(this, "User added successfully", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, ScheduleActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun updateUserInfo() {
        progressDialog.setMessage("Saving user info")

        val userID = firebaseAuth.uid

        //prepare data to add in database
        val userName = binding.userNameEditText.text.toString()
        val groupName = binding.groupNameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val user = User(userName, groupName, email, password)

        //init reference of database
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

        //reference of table 'Users'
        reference = database.getReference("Users")

        //add user data to database
        reference.child(userID!!).setValue(user)
            .addOnSuccessListener {
                //data successfully added to database
                progressDialog.dismiss()
                Toast.makeText(this, "Account created...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                //failed setting data in database
                progressDialog.dismiss()
                Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity
        return super.onSupportNavigateUp()
    }
}