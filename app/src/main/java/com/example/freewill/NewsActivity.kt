package com.example.freewill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freewill.databinding.ActivityNewsBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.NewsAdapter
import com.example.freewill.models.NewsModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.*

class NewsActivity : AppCompatActivity() {

//    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityNewsBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var newsList: ArrayList<NewsModel>
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsRecycler = binding.newsRecycler
        newsRecycler.layoutManager = LinearLayoutManager(this)
        newsRecycler.setHasFixedSize(true)
        //recyclerView.adapter = newsAdapter

        newsList = ArrayList()
        val referenceNews =
            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("News")
        referenceNews.get().addOnSuccessListener {
            for (newsSnapshot in it.children) {
                val news = NewsModel(
                    newsSnapshot.child("Header").value as String,
                    newsSnapshot.child("Description").value as String,
                    newsSnapshot.child("Date").value as String,
                    newsSnapshot.child("Url").value as String
                )
                newsList.add(news)
            }

            newsRecycler.adapter = NewsAdapter(this,newsList)
        }


        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_schedule)
        setSupportActionBar(toolbar)

//      Navigation bar
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer(this)
    }

}