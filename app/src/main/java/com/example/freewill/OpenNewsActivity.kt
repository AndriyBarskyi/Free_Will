package com.example.freewill

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.freewill.databinding.ActivityOpenNewsBinding
import com.example.freewill.image_tools.loadImage

class OpenNewsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOpenNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val animalIntent = intent
        val header = animalIntent.getStringExtra("header")
        val description = animalIntent.getStringExtra("description")
        val animalImg = animalIntent.getStringExtra("img")

        /**call text and images*/
        binding.header.text = header
        binding.description.text=description
        Glide.with(this)
            .load(animalImg)
            .into(binding.imgNews)
    }
}