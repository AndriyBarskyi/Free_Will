package Links

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.freewill.EditSchedule_Fragment
import com.example.freewill.R
import com.example.freewill.databinding.ActivityLinksPreviewBinding
import com.example.freewill.models.NavigationClass
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LinksPreviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLinksPreviewBinding
    private lateinit var referenceSubject: DatabaseReference
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var drawerLayout: DrawerLayout
    fun readSubjects(onSuccess:(Array<String>)->Unit) {
        referenceSubject = FirebaseDatabase
            .getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("Links")
        referenceSubject.get().addOnSuccessListener {
            val array=when (it.exists()) {
                true->
                {
                    var arrayKeys= Array<String>(1){"Натисни↓"}
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
        binding = ActivityLinksPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readSubjects(){array->CreateItem(array)}

        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = binding.toolbar

        // using toolbar as ActionBar
        toolbar.setTitle(R.string.toolbar_link)
        setSupportActionBar(toolbar)

//      Navigation bar
        drawerLayout = findViewById(R.id.drawerLayout)
        toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val navView: NavigationView = findViewById(R.id.navView)
        val navigation = NavigationClass(drawerLayout, toggle, navView, this)
        navigation.createNavigationDrawer(this)
    }
    fun CreateItem(languages:Array<String>)
    {
        val spinner = findViewById<Spinner>(R.id.spinner)
        val image1=findViewById(R.id.boy_links) as ImageView
        val image2=findViewById(R.id.imageView29) as ImageView
        val text1=findViewById(R.id.linkFond) as TextView
        val text2=findViewById(R.id.tvTitleFond) as TextView
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
                    if(languages[position]!="Натисни↓")
                    {
                        OpenFrag(LinkFragment.newInstance(languages[position]), R.id.frame)
                        //image=findViewById(R.id.boy_links) as ImageView
                        image1.visibility=View.GONE
                        image2.visibility=View.GONE
                        text1.visibility=View.GONE
                        text2.visibility=View.GONE
                    }
                    else
                    {
                        image1.visibility=View.VISIBLE
                        image2.visibility=View.VISIBLE
                        text1.visibility=View.VISIBLE
                        text2.visibility=View.VISIBLE
                    }

                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
