package com.example.freewill

import android.os.Bundle
import android.view.Gravity
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
import com.example.freewill.models.TeacherDataAdapter
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase


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
                    reviewBinding.root.resources.getIdentifier("@drawable/" + teacherSnapshot.child("photo").value as String?, null,
                        packageName
                    ).toString()
                )
                teachersArrayList.add(teacher)
            }
/*            var tc: TeacherCard = TeacherCard(
                it.child("avgRating").value as Double?,
                it.child("department").value as String?,
                it.child("fullName").value as String?,
                it.child("photo").value as String?
            )*/
            //teachersArrayList.add(tc)
            teachersRecyclerView.adapter = TeacherDataAdapter(this,teachersArrayList)
        }

        /*reviewBinding.search.setOnClickListener {
            reviewBinding.toolbar.title = ""
            reviewBinding.searchHere.isVisible
        }*/
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