package com.example.freewill
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.freewill.databinding.FragmentScheduleBinding
import com.example.freewill.models.ReadFirebase
import com.example.freewill.databinding.ActivityScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.ShowAudience
import com.example.freewill.search_point.Dijkstra
import org.w3c.dom.Text

const val ARG_OBJECT = "object"

@Suppress("UNREACHABLE_CODE")
class ScheduleFragment : Fragment() {
    private lateinit var binding: FragmentScheduleBinding
    private val dataModel : DataModel by activityViewModels()

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
            ReadGroup.readGroupUser(){group->ReadGroup.readSchedule(group,textDay.toString(),binding)}

        }
        clickOnAud()
    }

//    fun setOnClickListener() {
////if( binding.aud1.setOnClickListener{} || )
//        binding.aud1.setOnClickListener {
//            if(binding.aud1.toString()!="") {
//                dataModel.audutoria.value = binding.aud1.text as String?
//            }
//        }
//
//    }

    fun clickOnAud()
    {

        binding.aud1.setOnClickListener {
            if(binding.aud1.toString()!="") {
                dataModel.audutoria.value = binding.aud1.text as String?
            }
        }
        binding.aud2.setOnClickListener {
            if(binding.aud2.toString()!="") {
                dataModel.audutoria.value = binding.aud2.text as String?
            }
        }
        binding.aud3.setOnClickListener {
            if(binding.aud3.toString()!="") {
                dataModel.audutoria.value = binding.aud3.text as String?
            }
        }
        binding.aud4.setOnClickListener {
            if(binding.aud4.toString()!="") {
                dataModel.audutoria.value = binding.aud4.text as String?
            }
        }
        binding.aud5.setOnClickListener {
            if(binding.aud5.toString()!="") {
                dataModel.audutoria.value = binding.aud5.text as String?
            }
        }
    }
//не працює


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