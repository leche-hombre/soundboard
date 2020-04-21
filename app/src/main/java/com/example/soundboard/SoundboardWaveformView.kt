package com.example.soundboard

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.random.Random
import kotlin.random.nextInt

class SoundboardWaveformView : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val paint = Paint()
        paint.color = Color.BLUE
        paint.strokeWidth = 5f
        paint.style = Paint.Style.STROKE

        val startingPosition = this.height / 2

        // draw stuff here
        val path = Path()
        path.moveTo(0f, startingPosition.toFloat())

        for (i in 1..1000) {
            val y = Random.nextInt(1..100)
            path.lineTo(i.toFloat(), y.toFloat())
        }

        canvas?.drawPath(path, paint)
    }

}