package com.example.freewill

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.freewill.databinding.ActivityEditScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.Day
import com.example.freewill.models.ReadFirebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class EditScheduleActivity : AppCompatActivity() ,  View.OnClickListener
    {
        private lateinit var binding: ActivityEditScheduleBinding

//        val listClic = mutableListOf<TextView>(binding.monday,binding.tuesday,binding.wednesday,
//            binding.thursday,binding.friday)

        private val dataModel: DataModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityEditScheduleBinding.inflate(layoutInflater)
            this.setContentView(binding.root)

            binding.monday.setOnClickListener(this)
            binding.tuesday.setOnClickListener(this)
            binding.wednesday.setOnClickListener(this)
            binding.thursday.setOnClickListener(this)
            binding.friday.setOnClickListener(this)

            val toolbar: Toolbar = binding.toolbar

            // using toolbar as ActionBar
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                title = "Change Schedule"
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }

        }

        override fun onSupportNavigateUp(): Boolean {
            onBackPressed()
            return super.onSupportNavigateUp()
        }

        private fun OpenFrag(f:Fragment, idHolder:Int) {
            supportFragmentManager
                .beginTransaction()
                .replace(idHolder,f)
                .commit()
        }


        override fun onClick(v: View?) {
            when (v?.getId()) {
                R.id.monday ,R.id.tuesday,R.id.wednesday,R.id.thursday,R.id.friday ->{
                    v.setBackgroundColor(Color.BLACK)
                    OpenFrag(EditSchedule_Fragment.newInstance(), R.id.FramLayout)
                    dataModel.message.value = (v as TextView).contentDescription.toString()
                }
            }
        }
    }
