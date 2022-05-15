package com.example.freewill
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ScheduleAdapter(fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    private val items: List<String> = listOf<String>("Понеділок","Вівторок","Середа","Четвер","П'ятниця")
    @RequiresApi(Build.VERSION_CODES.N)
    val calendar: Calendar = Calendar.getInstance();
    @RequiresApi(Build.VERSION_CODES.N)
    val simpleDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE")
    @RequiresApi(Build.VERSION_CODES.N)
    val dataTime : String =simpleDateFormat.format(calendar.getTime())

    fun checkDay():Int{
        var position=0
        for (i in items){
            position = when (dataTime == i)
            {
                true->{
                    items.indexOf(i)
                    break}
                else->{continue}
            }
        }
        return position
    }

    override fun getItemCount(): Int = 5


    override fun createFragment(position: Int): Fragment {
        val fragment = ScheduleFragment()
        fragment.arguments = Bundle().apply {
            //var position=checkDay()
            putInt(ARG_OBJECT, position + 1)
            putString(ARG_OBJECT,items[position])
        }
        return fragment
    }

}