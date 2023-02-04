package com.example.aston_intensive_course_work.presentation.custom_view

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.aston_intensive_course_work.R
import kotlin.math.pow
import kotlin.math.sqrt

const val ANIMATION_DURATION = 700L

class CurtainView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attributeSet, defStyleAttr) {


    private var radius = 0F
    private val paint = Paint()

    val animationDuration: Long get() = ANIMATION_DURATION

    fun setRadius(radius: Float) {
        this.radius = radius
        invalidate()
    }

    private fun getMaxRadius(): Float {
        val x = width
        val y = height

        return sqrt(x.toDouble().pow(2.0) + y.toDouble().pow(2.0))
            .toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        paint.color = ContextCompat.getColor(context, R.color.white)
        canvas.drawCircle(0f, height.toFloat(), radius, paint)
        super.onDraw(canvas)
    }

    fun runAnimation() {
        val curtainAnimation = ObjectAnimator.ofFloat(this, "radius", 0F, getMaxRadius())
        curtainAnimation.duration = ANIMATION_DURATION
        curtainAnimation.interpolator = LinearInterpolator()

        val reverseAnimation = ObjectAnimator.ofFloat(this, "radius", getMaxRadius(), 0F)
        reverseAnimation.duration = 1

        val animationSet = AnimatorSet().apply {
            play(curtainAnimation).before(reverseAnimation)
        }

        animationSet.start()
    }
}