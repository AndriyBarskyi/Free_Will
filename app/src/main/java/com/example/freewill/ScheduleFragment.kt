package com.example.freewill
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.UNDERLINE_TEXT_FLAG
import com.example.freewill.databinding.FragmentScheduleBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.freewill.models.ReadFirebase
import com.example.freewill.models.ShowAudience
import com.example.freewill.search_point.Dijkstra

const val ARG_OBJECT = "object"

@Suppress("UNREACHABLE_CODE")
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var schedule:ScheduleActivity
    private var showing = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        залученийІнстаціїState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, InstanceState: Bundle?) {
        val ReadGroup = ReadFirebase()
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            //val position = getInt(ARG_OBJECT)
            val textDay =getString(ARG_OBJECT)
            ReadGroup.readGroupUser(){group->ReadGroup.ReadSchedule(group,textDay.toString(),binding)}

        //binding.class1.setText(textDay.toString())
            //binding.aud1.setText(position.toString())
        }
        showAudience()
//ReadGroup.readGroupUser(){group->ReadGroup.ReadSchedule(group,activity ,binding)}
    }
//не працює
    private fun showAudience()
    {
        showing=true
        var aud1 = binding.aud1.text.toString()
        aud1="240"
        binding.aud1.setOnClickListener {
            schedule = ScheduleActivity()
            schedule.setContentView(
                ShowAudience(
                    schedule.applicationContext,
                    Dijkstra.getCoord(aud1, activity)
                ),
            )
        }
    }

//    override fun onBackPressed() {
//        if (showing) {
//            showing = false
//
//            setContentView(R.layout.activity_schedule)
//            val i = Intent(baseContext, MapActivity::class.java)
//            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//
//            baseContext.startActivity(i)
//            finish()
//        }
//    }
}