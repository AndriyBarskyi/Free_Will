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
class ShowAudience(
    context: Context,
    point: ArrayList<Float>,
    attrs: AttributeSet? = null,
    defaultAttrs: Int = 0

) : View(context, attrs, defaultAttrs) {

    private var mLastTouchX = 0f
    private var mLastTouchY = 0f

    private var dX = 0f
    private var dY = 0f
    private var mScaleFactor = 1f

    private val audience = point


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

            restore()

        }

    }
}