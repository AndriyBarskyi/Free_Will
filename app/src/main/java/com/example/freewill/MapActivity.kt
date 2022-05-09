package com.example.freewill

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.DialogFragment
import com.google.firebase.database.DatabaseReference
import com.example.freewill.databinding.ActivityMapBinding
import com.example.freewill.models.NavigationClass
import com.example.freewill.search_point.Dijkstra
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase


class MapActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    lateinit var mapBinding: ActivityMapBinding
    val bitmap = Bitmap.createBitmap(913, 785, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapBinding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(mapBinding.root)
        createNavigationMenu()

        val findDirectionButton: FloatingActionButton = mapBinding.findDirectionButton
        findDirectionButton.setOnClickListener {
            val inflater =
                it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            drawerLayout = findViewById(R.id.drawerLayout)
            drawerLayout.foreground.alpha = 255
            val popupView: View = inflater.inflate(R.layout.activity_search_popup, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            val focusable = true
            val popupWindow = PopupWindow(popupView, width, height, focusable)

            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)
            popupWindow.isOutsideTouchable = true
            popupWindow.setOnDismissListener {
                drawerLayout.foreground.alpha = 0
            }

            val searchFromToButton: Button =
                popupWindow.contentView.findViewById(R.id.searchFromToButton)
            searchFromToButton.setOnClickListener {
                var fromPlace: String = ""
                var toPlace: String = ""
                val searchFromInput: TextInputEditText =
                    popupWindow.contentView.findViewById(R.id.searchFromInput)
                fromPlace = searchFromInput.text.toString()

                val searchToInput: TextInputEditText =
                    popupWindow.contentView.findViewById(R.id.searchToInput)
                toPlace = searchToInput.text.toString()
                if (toPlace != "" && fromPlace != "") {
                    drawLinesOnMap(fromPlace, toPlace)
                }
                popupWindow.dismiss()
            }
        }
        val switchInfo: Switch = findViewById(R.id.switch1)
        switchInfo.setOnCheckedChangeListener {ImageView, isChecked ->
            if (isChecked) {
                val imageMap: ImageView = findViewById(R.id.imageView16)
                imageMap.setOnTouchListener(imageViewOnTouchListener)
            }
        }
    }

    fun drawLinesOnMap(start: String, end: String) {
        var points = mutableListOf<Float>()
        points = Dijkstra.Calculate(start, end, this.baseContext)
        var paint = Paint()
        paint.setColor(Color.parseColor("#FF0000"))
        canvas.drawLines(points.toFloatArray(), paint)
    }

    fun createNavigationMenu() {
        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = mapBinding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_map)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawerLayout)
        drawerLayout.foreground.alpha = 0
        toggle =
            ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer()

    }

    class InfoRoomFragment(_room: String) : DialogFragment() {
        val room = _room
        var info="ss"
        lateinit var infoAboutRoom:DatabaseReference
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                infoAboutRoom = FirebaseDatabase
                    .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                    .getReference("Rooms")
                infoAboutRoom.child("237").get().addOnSuccessListener {
                    if (it.exists()) {
                        info=it.value.toString()

                    }
                }
                builder.setTitle("Інформація про ${room} ауд.").setMessage(info)
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val imageViewOnTouchListener =
            OnTouchListener { view, event ->
                val x = event.x.toInt()
                val y = event.y.toInt()
                if (5 <= x && x <= 200) {
                    val infoRoomFragment = InfoRoomFragment("238")
                    val manager = supportFragmentManager
                    infoRoomFragment.show(manager, "myDialog")
                    //val intent = Intent(this, NextActivity::class.java)
                    //context.startActivity(intent)
                }
                true
            }
}
