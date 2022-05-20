package com.example.freewill.models

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
import com.example.freewill.R


class NewsAdapter(private val context: Context, private val newsList: ArrayList<NewsModel>):
        RecyclerView.Adapter<NewsAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.news_recycleview_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNews = newsList[position]
        holder.header.text = currentNews.header
        holder.description.text = currentNews.description
        holder.date.text = currentNews.date

//        Glide.with(context)
//            .load(currentNews.newsImage)
//            .into(holder.newsImage)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val header : TextView = itemView.findViewById(R.id.header)
        val description : TextView = itemView.findViewById(R.id.description)
        val date : TextView = itemView.findViewById(R.id.date)
        //val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
    }


}