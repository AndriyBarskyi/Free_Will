package com.example.freewill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.freewill.databinding.ActivityReviewBinding

lateinit var reviewBinding: ActivityReviewBinding

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewBinding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(reviewBinding.root)
    }
}