package com.example.freewill.models

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.FeedbackTeacherActivity
import com.example.freewill.R
import com.google.android.material.imageview.ShapeableImageView


class TeacherDataAdapter(private val context: Context, private var teacherCardList: ArrayList<TeacherCard>) :
    RecyclerView.Adapter<TeacherDataAdapter.TeacherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.teacher_card, parent, false)
        return TeacherViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val currentItem = teacherCardList[position]
        holder.avgStarsRating.rating = currentItem.avgRating?.toFloat()!!
        holder.avgRating.text = "${currentItem.avgRating}/5"
        holder.department.text = currentItem.department
        holder.fullName.text = currentItem.fullName
        currentItem.photo?.let { holder.photo.setImageResource(it.toInt()) }

        holder.itemView.setOnClickListener {
            /**set Data*/
            val mIntent = Intent(context, FeedbackTeacherActivity::class.java)
            mIntent.putExtra("fullName",  holder.fullName.text)
            mIntent.putExtra("department", holder.department.text)
            mIntent.putExtra("photo", holder.photo.toString())
            mIntent.putExtra("avgRating", holder.avgRating.text)
            context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return teacherCardList.size
    }

    class TeacherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val fullName: TextView = itemView.findViewById(R.id.teacherFullName)
        val department: TextView = itemView.findViewById(R.id.departmentName)
        val photo: ShapeableImageView = itemView.findViewById(R.id.teacherPhoto)
        val avgStarsRating: RatingBar = itemView.findViewById(R.id.teacherRatingBar)
        val avgRating: TextView = itemView.findViewById(R.id.teacherAvgRating)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredList: ArrayList<TeacherCard>) {
        teacherCardList = filteredList
        notifyDataSetChanged()
    }
}