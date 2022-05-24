package com.example.freewill.models

import Links.LinksPreviewActivity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//class for better manipulating with navigation drawer
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

    fun createNavigationDrawer(activityClass: AppCompatActivity){
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.itemIconTintList = null
        navView.setNavigationItemSelectedListener{

            //determine what each button should do
            when(it.itemId){
                R.id.nav_schedule -> {
                    val i = Intent(context, ScheduleActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()
                }
                R.id.nav_map -> {
                    val i = Intent(context, MapActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()

                }
                R.id.nav_settings -> {
                    val i = Intent(context, SettingActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()

                }
                R.id.nav_log_out -> {
                    showAlertMessage(activityClass)

                }
                R.id.nav_news -> {
                    val i = Intent(context, NewsActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()

                }
                R.id.nav_feedback -> {
                    val i = Intent(context, ReviewActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()
                }
                R.id.nav_useful_links -> {
                    val i = Intent(context, LinksPreviewActivity::class.java)
                    context.startActivity(i)
                    activityClass.finish()
                }
            }
            true
        }
    }

    //open and show a custom alert dialog
    private fun showAlertMessage(activityClass: AppCompatActivity){
        val builder = AlertDialog.Builder(context)

        builder.setTitle(R.string.alert_header)
        builder.setMessage(R.string.log_out_alert)

        builder.setPositiveButton("YES") { dialog, which ->
            val firebaseAuth = Firebase.auth
            firebaseAuth.signOut()
            context.startActivity(Intent(context, LoginActivity::class.java))
            dialog.dismiss()
            activityClass.finish()

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