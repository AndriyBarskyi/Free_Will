package com.example.freewill.models

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.freewill.R

class DrawPoints(
    context: Context,
    points: ArrayList<Float>,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0

) : View(context, attrs, defaultAttrs) {

    private val point = points


    private val painter = Paint().apply {
        color = Color.rgb(4, 104, 84)
        strokeWidth = 30f
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas); // the default drawing
        canvas.apply {


            val mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.ourrrmap)

            val topRetreat = 500
            val bottomRetreat = height - topRetreat

            var p = toCanvas(point)



            canvas.drawBitmap(
                mapBitmap,
                null,
                Rect(0, topRetreat, width, bottomRetreat),
                painter
            )


            drawLines(p.toFloatArray(), painter)

            for (i in p.indices) {
                if (i % 2 == 1) {
                    continue;
                }
                drawCircle(p[i], p[i + 1], 15f,painter)
            }


        }

    }

    fun toCanvas(points: ArrayList<Float>): ArrayList<Float> {
        var p = ArrayList<Float>()

        val mapWidth = 913
        val mapHeight = 785

        val topRetreat = 500
        val bottomRetreat = 500


        for (i in points.indices) {
            if (i % 2 == 1) {
                continue;
            }
            p.add((points[i] / mapWidth) * width)
            p.add(((points[i + 1] / mapHeight) * (height - topRetreat - bottomRetreat) + topRetreat))
        }
        return p
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