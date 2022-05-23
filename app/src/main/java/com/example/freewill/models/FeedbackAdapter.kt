package com.example.freewill.models

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.freewill.R


class FeedbackAdapter(private var feedbacks: ArrayList<String>) :
    RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedbackViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.teacher_card, parent, false)
        return FeedbackViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeedbackViewHolder, position: Int) {
        val currentItem = feedbacks[position]
        holder.feedback.text = currentItem
    }

    override fun getItemCount(): Int {
        return feedbacks.size
    }

    class FeedbackViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val feedback: TextView = itemView.findViewById(R.id.feedbackCardText)
    }
}