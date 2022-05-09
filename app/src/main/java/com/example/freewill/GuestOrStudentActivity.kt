package com.example.freewill

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freewill.databinding.ActivityGuestOrStudentBinding
import com.example.freewill.databinding.ActivityLoginBinding

class GuestOrStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuestOrStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestOrStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studentButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.guestButton.setOnClickListener {
            val i: Intent = Intent(this, ScheduleActivity::class.java)
            i.putExtra("isGuest", true)
            startActivity(i)
            finish()
        }
    }


}