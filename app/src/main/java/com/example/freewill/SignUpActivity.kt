package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import com.example.freewill.databinding.ActivitySignUpBinding
import com.example.freewill.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivitySignUpBinding

    //Firebase Authorization
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()

        //sign up button event
        binding.signUpButton.setOnClickListener{
            validateUser()
        }
    }

    //check whether all fields contain correct data
    private fun validateUser(){
        val tempUser = binding.userNameEditText.text.toString().trim()
        val tempGroupName = binding.groupNameEditText.text.toString().trim()
        val tempEmail = binding.emailEditText.text.toString().trim()
        val tempPassword = binding.passwordEditText.text.toString().trim()


        if(!Patterns.EMAIL_ADDRESS.matcher(tempEmail).matches()){
            //invalid email
            binding.emailEditText.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(tempPassword)){
            //password is not entered
            binding.passwordEditText.error = "Please enter the password"
        }
        else if(tempPassword.length < 4){
            binding.passwordEditText.error = "Password must at least 4 characters long"
        }
        else if(TextUtils.isEmpty(tempUser)){
            binding.userNameEditText.error = "Please enter your nickname"
        }
        else if(TextUtils.isEmpty(tempGroupName)){
            binding.groupNameEditText.error = "Please enter your group"
        }
        else if(!isGroupCorrect(tempGroupName)){
            binding.groupNameEditText.error = "Enter group like 'ПМІ-14'"
        }
        else
        {
            //create user account in database and start new activity
            firebaseAuth.createUserWithEmailAndPassword(tempEmail, tempPassword)
                .addOnCompleteListener(SignUpActivity()) {task->

                    if(task.isSuccessful){
                        updateUserInfo()
                        startActivity(Intent(this, LetterEntranceActivity::class.java))
                        finish()

                    }
                    else{
                        Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    //check whether group is typed correctly
    private fun isGroupCorrect(groupName: String): Boolean{
        return groupName != "" && checkGroup(groupName)
    }

    //check whether group is in list of available groups of university/faculty
    private fun checkGroup(groupName: String): Boolean{
        val groupList = listOf("ПМІ-11", "ПМІ-12", "ПМІ-13", "ПМІ-14", "ПМІ-14", "ПМІ-16", "ПМО-21", "ПМК-21",
                                "ПМІ-21", "ПМІ-22", "ПМІ-23", "ПМІ-24", "ПМІ-25", "ПМО-21", "ПМК-21",)

        return groupName in groupList
    }

    //add new user info or update it in Firebase Database
    private fun updateUserInfo() {

        val userID = firebaseAuth.uid

        //prepare data to add in database
        val groupName = binding.groupNameEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()
        val userName = binding.userNameEditText.text.toString().trim()
        val user = User(groupName, password)

        val profileUpdates = userProfileChangeRequest {
            displayName = userName
        }

        val firebaseUser = firebaseAuth.currentUser
        firebaseUser!!.updateProfile(profileUpdates)

        //init reference of database
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

        //reference of table 'Users'
        reference = database.getReference("Users")

        //add user data to database
        reference.child(userID!!).setValue(user)
            .addOnSuccessListener {
                //data successfully added to database
                Toast.makeText(this, "Account created...", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{ e ->
                //failed setting data in database
                Toast.makeText(this, "Failed saving user info due to ${e.message}", Toast.LENGTH_SHORT).show()

            }
    }

    //navigation button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed() // go back to previous activity
        return super.onSupportNavigateUp()
    }
}