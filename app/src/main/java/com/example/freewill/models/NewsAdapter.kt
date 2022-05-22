package com.example.freewill.models

import android.content.Context
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.freewill.NewsActivity
import com.example.freewill.OpenNewsActivity
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
        holder.date.text = currentNews.date
//        holder.description.text = currentNews.description
        val tempText = currentNews.description
        holder.description.text = checkDescriptionLen(tempText)

        Glide.with(context)
            .load(currentNews.newsImage)
            .into(holder.newsImage)

        holder.itemView.setOnClickListener {
            /**set Data*/
            val mIntent = Intent(context, OpenNewsActivity::class.java)
            mIntent.putExtra("header",holder.header.text)
            mIntent.putExtra("date",holder.date.text)
            mIntent.putExtra("description",currentNews.description)
            mIntent.putExtra("img",currentNews.newsImage)
            context.startActivity(mIntent)

//            val bundle = Bundle()
//            bundle.putString("header", holder.header.text.toString())
//            bundle.putString("description", holder.description.text.toString())
//            bundle.putString("date", holder.date.text.toString())
//            bundle.putString("img", currentNews.newsImage.toString())
//            var fragment = OpenNewsFragment()
//            fragment.arguments = bundle
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val header : TextView = itemView.findViewById(R.id.header)
        val description : TextView = itemView.findViewById(R.id.description)
        val date : TextView = itemView.findViewById(R.id.date)
        val newsImage: ImageView = itemView.findViewById(R.id.newsImage)
    }

    public fun checkDescriptionLen(description: String): String{
        var resultString: String = ""
        if(description.length > 40){
            resultString = description.substring(0, 20)
            resultString += "..."
        }
        else{
            resultString = description
        }

        return resultString
    }
}