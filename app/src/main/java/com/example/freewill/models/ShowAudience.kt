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
            val bottomRetreat = 500

            canvas.drawBitmap(
                mapBitmap,
                null,
                Rect(0, topRetreat, width, bottomRetreat),
                painter
            )

            if (audience[0] != (-1).toFloat()) {
                drawPoint(
                    (audience[0] / mapWidth) * width,
                    (audience[1] / mapHeight) * (height - topRetreat - bottomRetreat) + topRetreat,
                    painter
                )
            }

        }

    }

//        @SuppressLint("ClickableViewAccessibility")
//        override fun onTouchEvent(event: MotionEvent): Boolean {
//            var motionTouchEventX = event.x
//            var motionTouchEventY = event.y
//
////            drawCircle(motionTouchEventX,motionTouchEventY,300f,painter)
//
////            when (event.action) {
////                MotionEvent.ACTION_DOWN -> touchStart()
////                MotionEvent.ACTION_MOVE -> touchMove()
////                MotionEvent.ACTION_UP -> touchUp()
////            }
//            return true
//        }
}