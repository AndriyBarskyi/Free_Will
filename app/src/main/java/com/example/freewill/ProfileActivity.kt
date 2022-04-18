package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.freewill.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //ViewBinding
    private lateinit var binding: ActivityProfileBinding

    //ActionBar
    private lateinit var actionBar: ActionBar

    //FirebaseAuthentication
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Configure ActionBar
        actionBar = supportActionBar!!
        actionBar.title = "Profile"

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()

        //logOut button
        binding.logOutButton.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        binding.nextButton.setOnClickListener{
            startActivity(Intent(this, ScheduleActivity::class.java))
        }
    }

    private fun checkUser() {
        //check whether user is logged in or not
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null){
            //user is logged in
            val userName = firebaseUser.email
            binding.userNameText.text = userName
        }
        else{
            //user is not logged in
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}