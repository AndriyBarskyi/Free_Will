package com.example.freewill

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.databinding.ActivityReviewBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.TeacherCard
import com.example.freewill.models.TeacherCardDataAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import java.util.*


private lateinit var teachersRecyclerView: RecyclerView
lateinit var teachersArrayList: ArrayList<TeacherCard>
lateinit var toggle: ActionBarDrawerToggle
lateinit var reviewBinding: ActivityReviewBinding
lateinit var drawerLayout: DrawerLayout

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewBinding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(reviewBinding.root)
        createNavigationMenu()

        teachersRecyclerView = reviewBinding.teachersRecycler
        teachersRecyclerView.layoutManager = LinearLayoutManager(this)
        teachersRecyclerView.setHasFixedSize(true)
        teachersArrayList = ArrayList()
        val referenceTeacher =
            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Teachers")
        referenceTeacher.get().addOnSuccessListener {
            for (teacherSnapshot in it.children) {
                val teacher = TeacherCard(
                    teacherSnapshot.child("avgRating").value as String?,
                    teacherSnapshot.child("department").value as String?,
                    teacherSnapshot.child("fullName").value as String?,
                    reviewBinding.root.resources.getIdentifier(
                        "@drawable/" + teacherSnapshot.child("photo").value as String?, null,
                        packageName
                    ).toString()
                )
                teachersArrayList.add(teacher)
            }
            teachersRecyclerView.adapter = TeacherCardDataAdapter(this, teachersArrayList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)

        // getting search view of our item.
        val searchView = searchItem.actionView as SearchView

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText)
                return false
            }
        })
        return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<TeacherCard> = ArrayList()

        // running a for loop to compare elements.
        for (item in teachersArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.fullName?.lowercase(Locale.ROOT)
                    ?.contains(text.lowercase(Locale.getDefault())) == true || item.department?.lowercase(
                    Locale.ROOT
                )?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, R.string.teachers_not_found, Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            teachersRecyclerView.adapter = TeacherCardDataAdapter(this, teachersArrayList)
            (teachersRecyclerView.adapter as TeacherCardDataAdapter).filterList(filteredList)
        }
    }

    private fun createNavigationMenu() {
        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = reviewBinding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_reviews)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 0
        toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer(this)
    }

}