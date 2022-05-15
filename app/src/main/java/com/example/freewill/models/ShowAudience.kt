package com.example.freewill.models

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.freewill.R

class ShowAudience(
    context: Context,
    point: ArrayList<Float>,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0

) : View(context, attrs, defaultAttrs) {

    private val audience = point


    private val painter = Paint().apply {
        color = Color.rgb(4, 104, 84)
        strokeWidth = 30f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas); // the default drawing
        canvas.apply {


            val mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.ourrrmap)

            val mapWidth = 913
            val mapHeight = 785

            val topRetreat = 500
            val bottomRetreat = height - topRetreat
            val bottomRetreatPoint = 500


            canvas.drawBitmap(
                mapBitmap,
                null,
                Rect(0, topRetreat, width, bottomRetreat),
                painter
            )

            if (audience[0] != (-1).toFloat()) {
                drawCircle(
                    (audience[0] / mapWidth) * width,
                    (audience[1] / mapHeight) * (height - topRetreat - bottomRetreatPoint) + topRetreat,
                    20f,
                    painter
                )
            }

        }

    }
}