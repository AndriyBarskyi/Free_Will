package com.example.freewill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freewill.databinding.ActivityFeedbackTeacherBinding
import com.example.freewill.databinding.ActivityScheduleBinding

class FeedbackTeacherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val TeacherIntent = intent
        val fullName = TeacherIntent.getStringExtra("fullName")
        val department = TeacherIntent.getStringExtra("department")
        val avgRating = TeacherIntent.getStringExtra("avgRating")
        val photo = TeacherIntent.getStringExtra("photo")

        /**call text and images*/
        binding.backgroundName.text = fullName
        binding.subjectName.text = department
        binding.rate.text = avgRating
        //binding.feedbackTeacherPhoto.let {photo.setImageResource(it.toInt()) }
        //img.loadImage(animalImg, getProgessDrawable(this))
    }
}