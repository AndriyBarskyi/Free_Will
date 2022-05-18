package Links

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.freewill.EditSchedule_Fragment
import com.example.freewill.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LinksPreviewActivity : AppCompatActivity() {
    private lateinit var referenceSubject: DatabaseReference
    fun readSubjects(onSuccess:(Array<String>)->Unit) {
        referenceSubject = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Links")
        referenceSubject.get().addOnSuccessListener {
            val array=when (it.exists()) {
                true->
                {
                    var arrayKeys= emptyArray<String>()
                    it.children.forEach {
                        arrayKeys+=it.key.toString()
                    }
                    arrayKeys

                }
                false-> arrayOf(" ")
            }
            onSuccess(array)
        }
    }
    private fun OpenFrag(f:Fragment, idHolder:Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(idHolder,f)
            .commit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_links_preview)
        OpenFrag(LinkFragment.newInstance(), R.id.frame)
        readSubjects(){array->CreateItem(array)}

        //supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView4, LinkFragment.newInstance()).commit()
    }
    fun CreateItem(languages:Array<String>)
    {
        val spinner = findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    Toast.makeText(this@LinksPreviewActivity,
                        "Обраний предмет: "+
                                "" + languages[position], Toast.LENGTH_SHORT).show()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }
}