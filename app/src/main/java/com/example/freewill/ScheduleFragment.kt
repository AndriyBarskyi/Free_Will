package com.example.freewill
import android.content.Intent
import android.graphics.Canvas
import com.example.freewill.databinding.FragmentScheduleBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.freewill.models.ReadFirebase
import com.example.freewill.models.ShowAudience
import com.example.freewill.search_point.Dijkstra
import com.google.firebase.database.FirebaseDatabase


const val ARG_OBJECT = "object"

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

    override fun onViewCreated(view: View, завантаженийInstanceState: Bundle?) {
        val ReadGroup = ReadFirebase()
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val position = getInt(ARG_OBJECT)
            val textDay =getString(ARG_OBJECT)
            ReadGroup.readGroupUser(){group->readSchedule(group,textDay.toString())}
            binding.class1.setText(textDay.toString())

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

    private fun readSchedule(user: String?, day:String?) {

        val referenceSchedule = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Shedule")
        referenceSchedule.child(user!!).child(day!!).get().addOnSuccessListener{
            if(it.exists())
            {
                val firstpara =it.child("firstpara").child("para").value
                val secondpara =it.child("secondpara").child("para").value
                val thirdpara =it.child("thirdpara").child("para").value
                val fourthpara =it.child("fourthpara").child("para").value
                val fifthpara =it.child("fifthpara").child("para").value

                val audut1 =it.child("firstpara").child("audutoria").value
                val audut2 =it.child("secondpara").child("audutoria").value
                val audut3 =it.child("thirdpara").child("audutoria").value
                val audut4 =it.child("fourthpara").child("audutoria").value
                val audut5 =it.child("fifthpara").child("audutoria").value

                binding.class1.setText(firstpara.toString())
                binding.class2.setText(secondpara.toString())
                binding.class3.setText(thirdpara.toString())
                binding.class4.setText(fourthpara.toString())
                binding.class5.setText(fifthpara.toString())

                binding.aud1.setText(audut1.toString())
                binding.aud2.setText(audut2.toString())
                binding.aud3.setText(audut3.toString())
                binding.aud4.setText(audut4.toString())
                binding.aud5.setText(audut5.toString())
                Toast.makeText(activity, "Shedule read...", Toast.LENGTH_SHORT).show()
            }
            else
            {
                Toast.makeText(activity, "Shedule not read!!!", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(activity, "Failed read shedule ", Toast.LENGTH_SHORT).show()
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