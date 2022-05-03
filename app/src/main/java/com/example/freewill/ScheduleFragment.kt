package com.example.freewill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.freewill.databinding.FragmentEditScheduleBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var database: FirebaseDatabase
    private lateinit var referenceSchedule: DatabaseReference
    private lateinit var binding: FragmentScheduleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userGrop="pmi_24"
        readSchedule(userGrop)
    }

        private fun readSchedule(user: String?) {

        val referenceSchedule = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Shedule")
        referenceSchedule.child(user!!).child("Понеділок").get().addOnSuccessListener{
            if(it.exists())
            {
                val firstpara =it.child("firstpara").value
                val secondpara =it.child("secondpara").value
                val thirdpara =it.child("thirdpara").value
                val fourthpara =it.child("fourthpara").value
                val fifthpara =it.child("fifthpara").value

                binding.class1.setText(firstpara.toString())
                binding.class2.setText(secondpara.toString())
                binding.class3.setText(thirdpara.toString())
                binding.class4.setText(fourthpara.toString())
                binding.class5.setText(fifthpara.toString())
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScheduleFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScheduleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}