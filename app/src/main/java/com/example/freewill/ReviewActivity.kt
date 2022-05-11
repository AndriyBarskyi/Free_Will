package com.example.freewill

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityReviewBinding
import com.example.freewill.models.NavigationClass
import com.google.android.material.navigation.NavigationView

lateinit var toggle: ActionBarDrawerToggle
lateinit var reviewBinding: ActivityReviewBinding
lateinit var drawerLayout: DrawerLayout

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reviewBinding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(reviewBinding.root)
        createNavigationMenu()
    }


    fun createNavigationMenu() {
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