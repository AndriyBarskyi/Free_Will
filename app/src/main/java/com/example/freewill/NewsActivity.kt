package com.example.freewill

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.databinding.ActivityLoginBinding
import com.example.freewill.databinding.ActivityNewsBinding
import com.example.freewill.models.NewsAdapter
import com.example.freewill.models.NewsModel
import com.google.firebase.database.*

class NewsActivity : AppCompatActivity() {

//    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityNewsBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private lateinit var newsList: ArrayList<NewsModel>
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")

        //reference of table 'Users'
        databaseReference = database.getReference("News")

//        newsList = ArrayList()

//        newsAdapter = NewsAdapter(this, newsList)
        getData()
        val recyclerView = binding.recylerView
        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
//        recyclerView.adapter = newsAdapter
    }


    private fun getData(){
        val newsList = ArrayList<NewsModel>()
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Get Post object and use the values to update the UI
                for(data in dataSnapshot.children){
                    var model = data.getValue(NewsModel::class.java)
                    newsList.add(model as NewsModel)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        databaseReference.addValueEventListener(postListener)
    }
}