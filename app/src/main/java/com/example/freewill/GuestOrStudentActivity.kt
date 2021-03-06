package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freewill.databinding.ActivityGuestOrStudentBinding

class GuestOrStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestOrStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestOrStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studentButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.guestButton.setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
            finish()
        }
    }


}