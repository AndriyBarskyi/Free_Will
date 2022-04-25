package com.example.freewill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.freewill.databinding.FragmentEditScheduleBinding
import com.example.freewill.models.DataModel
import com.example.freewill.models.Day



class EditSchedule_Fragment : Fragment() {
    private lateinit var binding: FragmentEditScheduleBinding
    private val dataModel : DataModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditScheduleBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditSchedule.setOnClickListener{
            val firstpara = binding.classEditText1.text.toString().trim()
            val secondpara = binding.classEditText2.text.toString().trim()
            val thirdpara = binding.classEditText3.text.toString().trim()
            val fourthpara = binding.classEditText4.text.toString().trim()
            val fifthpara = binding.classEditText5.text.toString().trim()

            val ChangeDay = Day(firstpara, secondpara, thirdpara, fourthpara, fifthpara)
            dataModel.message.value=ChangeDay;
        }

    }
    companion object {
        @JvmStatic
        fun newInstance() = EditSchedule_Fragment()
    }
}