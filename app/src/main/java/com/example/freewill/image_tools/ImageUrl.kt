package com.example.freewill.image_tools

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.freewill.R

fun getProgressDrawable(c: Context): CircularProgressDrawable{
    return CircularProgressDrawable(c).apply {
        strokeWidth = 5f
        centerRadius = 40f
        start()
    }
}

fun ImageView.loadImage(uri:String?, progressDrawable: CircularProgressDrawable){
    val option = RequestOptions().placeholder(progressDrawable).error(R.mipmap.ic_launcher)

    Glide.with(context)
        .setDefaultRequestOptions(option)
        .load(uri)
        .into(this)
}

//@BindingAdapter("android:imageUrl")
//fun loadImage(view: ImageView, url:String){
//    view.loadImage(url, getProgressDrawable(view.context))
//}

