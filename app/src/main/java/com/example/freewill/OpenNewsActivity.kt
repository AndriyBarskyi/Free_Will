package com.example.freewill

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.example.freewill.databinding.ActivityOpenNewsBinding
import com.example.freewill.image_tools.loadImage
import com.example.freewill.models.NewsAdapter
import com.example.freewill.models.NewsModel
import com.google.firebase.database.FirebaseDatabase

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

        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.toolbar_news)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
//        val referenceNews =
//            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
//                .getReference("News")
//        referenceNews.get().addOnSuccessListener {
//            for (newsSnapshot in it.children) {
//                val news = NewsModel(
//                    newsSnapshot.child("Header").value as String,
//                    newsSnapshot.child("Description").value as String,
//                    newsSnapshot.child("Date").value as String,
//                    newsSnapshot.child("Url").value as String
//                )
//
//            }
//        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}