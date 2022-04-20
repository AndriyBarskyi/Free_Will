package com.example.freewill

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.freewill.databinding.ActivitySearchPopupBinding

class SearchPopUpActivity : AppCompatActivity() {

    lateinit var searchPopupBinding: ActivitySearchPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        setContentView(R.layout.activity_search_popup)
        searchPopupBinding = ActivitySearchPopupBinding.inflate(layoutInflater)
    }
}