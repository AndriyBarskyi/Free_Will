package com.example.freewill

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import com.example.freewill.databinding.ActivityMapBinding
import com.example.freewill.search_point.Dijkstra
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import java.util.ArrayList


class MapActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var mapBinding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(mapBinding.root)

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = mapBinding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_map)
        setSupportActionBar(toolbar)


        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 0
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayShowHomeEnabled(true)

        val navView: NavigationView = findViewById(R.id.navView)
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> {
                    val i = Intent(this, ScheduleActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_map -> {
                    val i = Intent(this, MapActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
                R.id.nav_settings -> {
                    val i = Intent(this, SettingActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(i)
                }
            }
            true
        }

    }

    @SuppressLint("ClickableViewAccessibility", "InflateParams")
    fun onClickOpenPopUp(view:View) {
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        val inflater =
            view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 255
        val popupView: View = inflater.inflate(R.layout.activity_search_popup, null)
        val width = LinearLayout.LayoutParams.MATCH_PARENT
        val height = LinearLayout.LayoutParams.MATCH_PARENT
        val focusable = true
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener {
            drawerLayout.foreground.alpha = 0
        }

        val findDirectionBtn: Button = popupWindow.contentView.findViewById(R.id.searchFromToButton)
        findDirectionBtn.setOnClickListener {
            var fromPlace: String = ""
            var toPlace: String = ""
            val searchFromInput: TextInputEditText = popupWindow.contentView.findViewById(R.id.searchFromInput)
            fromPlace = searchFromInput.text.toString()

            val searchToInput: TextInputEditText = popupWindow.contentView.findViewById(R.id.searchToInput)
            toPlace = searchToInput.text.toString()
            if (fromPlace != "" && toPlace != "") {
                val points: ArrayList<Int>? = Dijkstra.Calculate(fromPlace, toPlace, this.baseContext)
                if (points != null) {
                    for (i in points){
                        Log.d("Point123", i.toString())
                    }
                }
            }
        }
    }
    class InfoRoomFragment (_room : String): DialogFragment()
    {
        val room=_room
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Інформація про ${room} ауд.")
                    .setMessage("Кафедра...")
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }
    fun aud237(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud238(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("238")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud239(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud240(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud241(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud241a(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud242(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud243(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud244(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }
    fun aud245(view: View)
    {
        //Toast.makeText(this,"Aud 237 Information", Toast.LENGTH_SHORT).show()
        val infoRoomFragment = InfoRoomFragment("237")
        val manager = supportFragmentManager
        infoRoomFragment.show(manager, "myDialog")
    }

}