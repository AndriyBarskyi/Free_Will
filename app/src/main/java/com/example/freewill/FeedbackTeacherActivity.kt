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
import androidx.drawerlayout.widget.DrawerLayout
import com.example.freewill.databinding.ActivityFeedbackTeacherBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

class FeedbackTeacherActivity : AppCompatActivity() {
    private lateinit var feedbackLayout: DrawerLayout
    private lateinit var binding: ActivityFeedbackTeacherBinding

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedbackTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teacherIntent = intent
        val fullName = teacherIntent.getStringExtra("fullName")
        val department = teacherIntent.getStringExtra("department")
        val avgRating = teacherIntent.getStringExtra("avgRating")
        val photo = teacherIntent.getStringExtra("photo")

        /**call text and images*/
        binding.backgroundName.text = fullName
        binding.departmentName.text = department
        binding.rate.text = avgRating
        if (photo != null) {
            binding.feedbackTeacherPhoto.setImageResource(photo.toInt())
        }
        if (avgRating != null) {
            if (avgRating != "0/5") {
                val referenceTeacher =
                    fullName?.let {
                        FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                            .getReference("Teachers").child(it)
                    }
                referenceTeacher?.get()?.addOnSuccessListener {
                    binding.modernityRating.rating = it.child("modernity").value as Float
                    binding.demandingRating.rating = it.child("demanding").value as Float
                    binding.loyaltyRating.rating = it.child("loyalty").value as Float
                    binding.teachingSkillsRating.rating = it.child("teachingSkills").value as Float
                }
            }
        }

        binding.addFeedbackFAB.setOnClickListener {
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
                    loyalty.compareTo(0) != 0 && teachingSkills.compareTo(0) != 0) {
                    val referenceTeacher =
                        fullName?.let {
                            FirebaseDatabase.getInstance("https://freewilldatabase-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference("Teachers").child(it)
                        }
                    referenceTeacher?.get()?.addOnSuccessListener {

                    }
                }

                if (feedback.trim() != ""){

                }
            }
        }
    }
}