package com.example.freewill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.example.freewill.databinding.FragmentEditScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.Day
import com.example.freewill.models.Para
import com.example.freewill.models.ReadFirebase


class EditSchedule_Fragment : Fragment() {
    private lateinit var binding: FragmentEditScheduleBinding
    private val dataModel : DataModel by activityViewModels()
    private lateinit var ChangeSchedule : ReadFirebase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ChangeSchedule = ReadFirebase()
        binding.btnEditSchedule.setOnClickListener {
            val firstpara = binding.classEditText1.text.toString().trim()
            val secondpara = binding.classEditText2.text.toString().trim()
            val thirdpara = binding.classEditText3.text.toString().trim()
            val fourthpara = binding.classEditText4.text.toString().trim()
            val fifthpara = binding.classEditText5.text.toString().trim()

            val audut1 = binding.audEditText1.text.toString().trim()
            val audut2 = binding.audEditText2.text.toString().trim()
            val audut3 = binding.audEditText3.text.toString().trim()
            val audut4 = binding.audEditText4.text.toString().trim()
            val audut5 = binding.audEditText5.text.toString().trim()

            val ChangeDay = Day(
                Para(firstpara, audut1), Para(secondpara, audut2),
                Para(thirdpara, audut3), Para(fourthpara, audut4), Para(fifthpara, audut5)
            )
            var item: String = ""
            dataModel.message.observe(
                requireActivity(),
            ) {
                item = it
            }
            ChangeSchedule.UpdateShedule(ChangeDay, item, requireActivity() as AppCompatActivity)

        }
    }
    companion object {
        @JvmStatic
        fun newInstance() = EditSchedule_Fragment()
    }
}