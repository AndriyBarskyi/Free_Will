package Links

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freewill.R
import com.example.freewill.databinding.ActivityNewsBinding
import com.example.freewill.databinding.FragmentLinkBinding




class LinkFragment : Fragment() {
    private lateinit var binding: FragmentLinkBinding
    private val adapter = LinksAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLinkBinding.inflate(layoutInflater)
        init()
        return binding.root
    }
    private fun init(){
        binding.apply {
            recView.layoutManager=LinearLayoutManager(activity)
            recView.adapter = adapter
            adapter.AddLinks("C++")
        }
    }


}