package com.example.freewill.models

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.navigation.NavigationView
import android.view.View
import android.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.*
import com.google.firebase.auth.FirebaseAuth


class NavigationClass(
    _drawerLayout: DrawerLayout,
    _toggle: ActionBarDrawerToggle,
    _navView: NavigationView,
    _context: Context
) {
    val drawerLayout = _drawerLayout
    val toggle = _toggle
    val navView = _navView
    val context = _context

    fun createNavigationDrawer(){
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_schedule -> {
                    val i = Intent(context, ScheduleActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(i)
                }
                R.id.nav_map -> {
                    val i = Intent(context, MapActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(i)
                }
                R.id.nav_settings -> {
                    val i = Intent(context, SettingActivity::class.java)
//                    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(i)
                }
                R.id.nav_log_out -> {
                    showAlertMessage()
                }

            }
            true
        }
    }

    fun showAlertMessage(){
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.alert_header)
        builder.setMessage(R.string.log_out_alert)

        builder.setPositiveButton("YES") { dialog, which ->
            val firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            context.startActivity(Intent(context, LoginActivity::class.java))
            dialog.dismiss()
        }

        builder.setNegativeButton(
            "NO"
        ) { dialog, which -> // Do nothing
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()
    }

}