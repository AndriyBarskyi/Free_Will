package Links

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.freewill.R
import com.example.freewill.databinding.FragmentLinkBinding





class LinkFragment (_subject:String): Fragment() {
    private lateinit var binding: com.example.freewill.databinding.FragmentLinkBinding
    private val adapter = LinksAdapter()
    private val subject=_subject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_link,container,false)
        binding = FragmentLinkBinding.inflate(layoutInflater)
        init()
        return binding.root

    }
    private fun init(){
        binding.apply {
            recView.layoutManager=LinearLayoutManager(activity)
            recView.adapter = adapter
            adapter.AddLinks(subject)
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(subject:String)=LinkFragment(subject)
    }

}