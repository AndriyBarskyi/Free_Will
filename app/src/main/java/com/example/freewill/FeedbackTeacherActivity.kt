package com.example.freewill

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RatingBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.databinding.ActivityFeedbackTeacherBinding
import com.example.freewill.models.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class FeedbackTeacherActivity : AppCompatActivity() {
    lateinit var feedbackBinding: ActivityFeedbackTeacherBinding
    lateinit var teacherFeedbacks: ArrayList<String>
    private lateinit var feedbacksRecyclerView: RecyclerView

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        feedbackBinding = ActivityFeedbackTeacherBinding.inflate(layoutInflater)
        setContentView(feedbackBinding.root)

        val teacherIntent = intent
        val fullName = teacherIntent.getStringExtra("fullName")
        val department = teacherIntent.getStringExtra("department")
        val avgRating = teacherIntent.getStringExtra("avgRating")
        val photo = teacherIntent.getStringExtra("photo")

        val toolbar: Toolbar = feedbackBinding.toolbar

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = getString(R.string.information_about_teacher)
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        /**call text and images*/
        feedbackBinding.backgroundName.text = fullName
        feedbackBinding.departmentName.text = department
        feedbackBinding.rate.text = avgRating
        if (photo != null) {
            feedbackBinding.feedbackTeacherPhoto.setImageResource(photo.toInt())
        }
        feedbacksRecyclerView = feedbackBinding.feedbackRecycler
        feedbacksRecyclerView.layoutManager = LinearLayoutManager(this)
        feedbacksRecyclerView.setHasFixedSize(true)
        teacherFeedbacks = ArrayList()

        val referenceTeacher =
            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("Teachers").child("reviews")
        referenceTeacher.get().addOnSuccessListener {
            for (feedbackSnapshot in it.children) {
                teacherFeedbacks.add(feedbackSnapshot.value.toString())
            }
            feedbacksRecyclerView.adapter = FeedbackAdapter(teacherFeedbacks)
        }

        if (avgRating != null) {
            if (avgRating != "0/5") {
                val referenceDB =
                    fullName?.let {
                        FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Teachers").child(it)
                    }
                referenceDB?.get()?.addOnSuccessListener {
                    feedbackBinding.modernityRating.rating = it.child("modernity").value as Float
                    feedbackBinding.demandingRating.rating = it.child("demanding").value as Float
                    feedbackBinding.loyaltyRating.rating = it.child("loyalty").value as Float
                    feedbackBinding.teachingSkillsRating.rating = it.child("teachingSkills").value as Float
                }
            }
        }

        feedbackBinding.addFeedbackFAB.setOnClickListener {
            val inflater =
                it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popupView: View = inflater.inflate(R.layout.add_feedback_popup, null)
            val width = LinearLayout.LayoutParams.MATCH_PARENT
            val height = LinearLayout.LayoutParams.MATCH_PARENT
            val focusable = true
            val popupWindow = PopupWindow(popupView, width, height, focusable)

            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)
            popupWindow.isOutsideTouchable = true

            val addFeedbackBtn: Button = popupView.findViewById(R.id.addFeedbackBtn)
            addFeedbackBtn.setOnClickListener {
                val modernity: Float =
                    (popupView.findViewById(R.id.addModernityRating) as RatingBar).rating
                val demanding: Float =
                    (popupView.findViewById(R.id.addDemandingRating) as RatingBar).rating
                val loyalty: Float =
                    (popupView.findViewById(R.id.addLoyaltyRating) as RatingBar).rating
                val teachingSkills: Float =
                    (popupView.findViewById(R.id.addTeachingSkillsRating) as RatingBar).rating
                val feedback: String =
                    (popupView.findViewById(R.id.feedbackText) as TextInputEditText).text.toString()
                if (modernity.compareTo(0) != 0 && demanding.compareTo(0) != 0 &&
                    loyalty.compareTo(0) != 0 && teachingSkills.compareTo(0) != 0
                ) {
                    var teacherRatings: TeacherRatings = TeacherRatings()
                    val referenceTeacher =
                        fullName?.let {
                            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Teachers").child(it)
                        }
                    if (avgRating != null) {
                        referenceTeacher?.setValue(
                            TeacherRatings(
                                modernity,
                                demanding,
                                loyalty,
                                teachingSkills,
                                0,
                                avgRating.split("/")[0].toFloat()
                            )
                        )
                    }
                }

                if (feedback.trim() != "") {
                    if (fullName != null) {
                        ReadFirebase().addReview(fullName, feedback, this)
                    }
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}