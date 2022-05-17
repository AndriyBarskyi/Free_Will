package com.example.freewill
import android.graphics.Canvas
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

class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private lateinit var schedule:ScheduleActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        залученийІнстаціїState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, завантаженийInstanceState: Bundle?) {
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

}