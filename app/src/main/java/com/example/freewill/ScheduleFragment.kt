package com.example.freewill
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.freewill.databinding.FragmentScheduleBinding
import com.example.freewill.models.ReadFirebase
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.models.ShowAudience
import com.example.freewill.search_point.Dijkstra
import org.w3c.dom.Text

const val ARG_OBJECT = "object"

@Suppress("UNREACHABLE_CODE")
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var bindingScheduleBinding: ActivityScheduleBinding
    private lateinit var schedule:ScheduleActivity
    private var showing = false
    private lateinit var thisContext: Context
    private lateinit var aud1: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        залученийІнстаціїState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)
        bindingScheduleBinding = ActivityScheduleBinding.inflate(layoutInflater)
//        if (container != null) {
//            vcontext = container.context
//        }
        thisContext = bindingScheduleBinding.root.context
        schedule = ScheduleActivity()
        showAudience(this)
        return binding.root
    }

    override fun onViewCreated(view: View, InstanceState: Bundle?) {
        val ReadGroup = ReadFirebase()
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            //val position = getInt(ARG_OBJECT)
            val textDay =getString(ARG_OBJECT)
            ReadGroup.readGroupUser(){group->ReadGroup.readSchedule(group,textDay.toString(),binding)}

        //binding.class1.setText(textDay.toString())
            //binding.aud1.setText(position.toString())
        }
//ReadGroup.readGroupUser(){group->ReadGroup.ReadSchedule(group,activity ,binding)}
    }

//не працює
    private fun showAudience(fragment: ScheduleFragment)
    {
        showing=true
        val aud1 = "240"
        val p = ArrayList<Float>()
        p.add(566f)
        p.add(566f)
        binding.aud1.setOnClickListener {
            schedule.setContentView(
                ShowAudience(schedule, p),
            )
        }
        binding.aud2.setOnClickListener {
            schedule = ScheduleActivity()
            schedule.setContentView(
                ShowAudience(bindingScheduleBinding.root.context, Dijkstra.getCoord(aud1, bindingScheduleBinding.root.context)
                ),
            )
        }
        binding.aud3.setOnClickListener {
            schedule = ScheduleActivity()
            schedule.setContentView(
                ShowAudience(bindingScheduleBinding.root.context, Dijkstra.getCoord(aud1, activity)
                ),
            )
        }
        binding.aud4.setOnClickListener {
            schedule = ScheduleActivity()
            schedule.setContentView(
                ShowAudience(bindingScheduleBinding.root.context, Dijkstra.getCoord(aud1, activity)
                ),
            )
        }
        binding.aud5.setOnClickListener {
            schedule = ScheduleActivity()
            schedule.setContentView(
                ShowAudience(bindingScheduleBinding.root.context, Dijkstra.getCoord(aud1, activity)
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