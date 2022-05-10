package com.example.freewill.search_point

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.R
import com.example.freewill.databinding.FragmentLinkBinding
import com.example.freewill.databinding.FragmentScheduleBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LinkFragment() : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var referenceLink: DatabaseReference
    private lateinit var binding: FragmentLinkBinding

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
        binding = FragmentLinkBinding.inflate(inflater)
        return binding.root
    }
    private fun readLink(subject:String) {

        referenceLink = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Links")
        referenceLink.child(subject).get().addOnSuccessListener {
            if (it.exists()) {
                val arrayLinks = it.value
                //binding.class1.setText(arrayLinks.toString())
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LinkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
class CustomAdapter(private val dataSet: Array<String>) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.textView)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.fragment_link, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSet[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
