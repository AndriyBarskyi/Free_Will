package com.example.freewill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freewill.databinding.ActivityFeedbackTeacherBinding

class FeedbackTeacherActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackTeacherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teacherIntent = intent
        val fullName = teacherIntent.getStringExtra("fullName")
        val department = teacherIntent.getStringExtra("department")
        val avgRating = teacherIntent.getStringExtra("avgRating")
        val photo = teacherIntent.getStringExtra("photo")

        /**call text and images*/
        binding.backgroundName.text = fullName
        binding.subjectName.text = department
        binding.rate.text = avgRating
        //binding.feedbackTeacherPhoto.let {photo.setImageResource(it.toInt()) }
        //img.loadImage(animalImg, getProgessDrawable(this))
    }
}