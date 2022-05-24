package com.example.freewill

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.NavigationClass
import com.example.freewill.models.ShowAudience
import com.example.freewill.search_point.Dijkstra
import com.google.android.material.navigation.NavigationView


class ScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScheduleBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    private lateinit var adapter: ScheduleAdapter
    private lateinit var viewPager: ViewPager2
    private var showing = false
    private val dataModel: DataModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendar: Calendar = Calendar.getInstance();
        val simpleDateFormat: SimpleDateFormat= SimpleDateFormat("EEEE dd.MM.yyyy")
        val dataTime : String =simpleDateFormat.format(calendar.getTime());
        binding.textView2.setText(dataTime);

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

    override fun onResume() {
        super.onResume()
        adapter = ScheduleAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter=adapter
        var aud=""
        dataModel.audutoria.observe(
            this,
        ) {
            aud = it
            setContentView(ShowAudience(this, Dijkstra.getCoord("$aud", this.baseContext)))
        }
        binding.ChangeBtn1.setOnClickListener {
            startActivity(Intent(this, EditScheduleActivity::class.java))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (showing) {
            showing = false

            setContentView(R.layout.activity_schedule)
            val i = Intent(baseContext, ScheduleActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            baseContext.startActivity(i)
            finish()
        } else {
            val i = Intent(baseContext, ScheduleActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            baseContext.startActivity(i)
            finish()
        }
    }
}