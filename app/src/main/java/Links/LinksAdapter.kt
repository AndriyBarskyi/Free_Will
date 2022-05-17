package Links

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.R
import com.example.freewill.databinding.LinkBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LinksAdapter:RecyclerView.Adapter<LinksAdapter.LinksHolder>() {
    private lateinit var referenceLink: DatabaseReference
    val linksList=ArrayList<Links>()
    fun readLinks(subject:String,onSuccess:(Array<String>)->Unit) {
        referenceLink = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Links")
        referenceLink.child(subject).get().addOnSuccessListener {
            val array = when (it.exists()) {
                true -> {
                    var arrayKeys = emptyArray<String>()
                    it.children.forEach {
                        arrayKeys += it.value.toString()
                    }
                    arrayKeys

                }
                false -> arrayOf(" ")
            }
            onSuccess(array)
        }
    }
    fun AddLinks(subject:String)
    {
        readLinks(subject){array->createList(array)}
    }
    @SuppressLint("NotifyDataSetChanged")
    fun createList(array:Array<String>)
    {
        array.forEach{
            linksList.add(Links(it))
            notifyDataSetChanged()
        }
    }

    class LinksHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = LinkBinding.bind(item)
        fun bind(link: Links)
        {
            binding.tvTitle.text=link.title
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LinksHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.link,parent,false)
        return LinksHolder(view)
    }

    override fun onBindViewHolder(holder: LinksHolder, position: Int) {
        holder.bind(linksList[position])
    }

    override fun getItemCount(): Int {
        return linksList.size
    }
}