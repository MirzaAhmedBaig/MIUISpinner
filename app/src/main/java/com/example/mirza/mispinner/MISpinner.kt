package com.example.mirza.mispinner

import android.animation.FloatEvaluator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

class MISpinner : View {
    private val outerCirclePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = width * 0.058f
            color = Color.parseColor("#299AE7")
            style = Paint.Style.STROKE
        }
    }
    private val innerCirclePaint by lazy {
        Paint().apply {
            isAntiAlias = true
            strokeWidth = width * 0.03f
            color = Color.parseColor("#88EAFB")
            style = Paint.Style.STROKE
        }
    }

    private val radius by lazy {
        width * 0.46f
    }

    private val gap by lazy {
        width * 0.04f
    }

    private var verticalOffset = 0f
    private var horizontalOffset = 0f
    private val handlerThread = Handler()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        /*val a = context.theme.obtainStyledAttributes(
                attrs,
                R.styleable.RangeView,
                0, 0)
        viewSize = a.getInteger(R.styleable.RangeView_view_size, 0)
        range = a.getInteger(R.styleable.RangeView_range, 3)
        a.recycle()*/
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private val runnable = Runnable {
        ValueAnimator.ofFloat(0f, 1f).apply {
            interpolator = AccelerateDecelerateInterpolator()
            duration = 1000
            repeatCount = INFINITE
            repeatMode = REVERSE
            setEvaluator(FloatEvaluator())
            addUpdateListener { valueAnimator ->
                horizontalOffset = (valueAnimator.animatedValue as Float) * gap
                invalidate()
            }
        }.start()

    }

    fun start() {
        handlerThread.post(runnable)
    }

    private fun init() {}

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawCircle(width * 0.5f, height * 0.5f, radius, outerCirclePaint)
//        canvas?.drawCircle(width*0.5f,height*0.5f,width*0.48f,innerCirclePaint)
        canvas?.drawOval(gap + horizontalOffset, (height / 2f) - radius + verticalOffset, width * 1.0f - gap - horizontalOffset, (height / 2f) + radius - verticalOffset, innerCirclePaint)
    }
}