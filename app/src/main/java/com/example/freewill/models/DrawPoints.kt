package com.example.freewill.models

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.example.freewill.R
import kotlin.math.max
import kotlin.math.min


@SuppressLint("ViewConstructor")
class DrawPoints(
    context: Context,
    points: ArrayList<Float>,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0

) : View(context, attrs, defaultAttrs) {
    private var mLastTouchX = 0f
    private var mLastTouchY = 0f

    private var dX = 0f
    private var dY = 0f
    private var mScaleFactor = 1f


    private val point = points


    private val painter = Paint().apply {
        color = Color.rgb(0, 180, 216)
        strokeWidth = 30f
    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            mScaleFactor = max(1f, min(mScaleFactor, 10f))

            invalidate()
            return true
        }
    }

    private val mScaleDetector = ScaleGestureDetector(context, scaleListener)

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        // Let the ScaleGestureDetector inspect all events.
        if (ev.pointerCount == 1) {

            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    mLastTouchX = ev.getX(0)
                    mLastTouchY = ev.getY(0)
                }
                MotionEvent.ACTION_MOVE -> {
                    var scX = (ev.getX(0) - mLastTouchX) / mScaleFactor
                    var scY = (ev.getY(0) - mLastTouchY) / mScaleFactor

                    val a = 0.25

                    if (dX + scX >= width * a || dX + scX <= -width * a) {
                        scX = 0f
                    }

                    if (dY + scY >= height * a || dY + scY <= -height * a) {
                        scY = 0f
                    }

                    Log.println(Log.INFO, "dx", dX.toString())
                    Log.println(Log.INFO, "dy", dY.toString())

                    dX += scX
                    dY += scY

                    mLastTouchX = ev.getX(0)
                    mLastTouchY = ev.getY(0)
                }

            }
            invalidate()


        } else {
            mScaleDetector.onTouchEvent(ev)
        }
        return true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas) // the default drawing
        canvas.apply {
            save()
            scale(mScaleFactor, mScaleFactor, (width / 2).toFloat(), (height / 2).toFloat())
            translate(dX, dY)
            Log.println(Log.INFO, "1", "1")


            val mapBitmap = BitmapFactory.decodeResource(resources, R.drawable.ourrrmap)

            val topRetreat = 500
            val bottomRetreat = height - topRetreat

//            val topRetreat = height/2
//            val bottomRetreat = height - topRetreat

            drawBitmap(
                mapBitmap,
                null,
                Rect(0, topRetreat, width, bottomRetreat),
                painter
            )

            val p = toCanvas(point)

            drawLines(p.toFloatArray(), painter)

            for (i in p.indices) {
                if (i % 2 == 1) {
                    continue
                }
                drawCircle(p[i], p[i + 1], 15f, painter)
            }

            restore()
        }

    }


    private fun toCanvas(points: ArrayList<Float>): ArrayList<Float> {
        val p = ArrayList<Float>()

        val mapWidth = 913
        val mapHeight = 785

        val topRetreat = 500
        val bottomRetreat = 500


        for (i in points.indices) {
            if (i % 2 == 1) {
                continue
            }
            p.add((points[i] / mapWidth) * width)
            p.add(((points[i + 1] / mapHeight) * (height - topRetreat - bottomRetreat) + topRetreat))
        }
        return p
    }
}