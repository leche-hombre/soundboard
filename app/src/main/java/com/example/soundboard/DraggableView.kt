package com.example.soundboard

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton


// TODO: Make this extend view, then other view components can extend from this
// TODO: Create a layout that's like: [bin][timer][draggable button], holding the button scales it up and starts the time
class DraggableView : Activity() {

    private var xPositionDelta: Int = 0
    private var xOriginalPosition: Int = 0

    private lateinit var tvSlideToCancel: TextView
    private lateinit var ivCancel: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.draggable_view)
        val btnDraggable = this.findViewById<FloatingActionButton>(R.id.btnDrag)
        val layoutParams: ConstraintLayout.LayoutParams = btnDraggable.layoutParams as ConstraintLayout.LayoutParams
        tvSlideToCancel = this.findViewById(R.id.tv_slide_to_cancel)
        ivCancel = this.findViewById(R.id.iv_cancel)
        xOriginalPosition = layoutParams.leftMargin
        btnDraggable.setOnTouchListener {view, motionEvent -> onTouch(view, motionEvent) }
    }

    private fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        val xCurrentPosition: Int = motionEvent.rawX.toInt()
        val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams

        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                xPositionDelta = xCurrentPosition - layoutParams.leftMargin
                tvSlideToCancel.visibility = View.VISIBLE
                ivCancel.visibility = View.VISIBLE
                setScaleFactor(view, 1.2f)
            }
            MotionEvent.ACTION_UP -> {
                view.performClick()
                layoutParams.leftMargin = xOriginalPosition
                view.layoutParams = layoutParams
                tvSlideToCancel.visibility = View.INVISIBLE
                ivCancel.visibility = View.INVISIBLE
                setScaleFactor(view, 1f)
            }
            MotionEvent.ACTION_MOVE -> {
                if (xCurrentPosition - xPositionDelta < xOriginalPosition) {
                    layoutParams.leftMargin = xCurrentPosition - xPositionDelta
                    view.layoutParams = layoutParams
                }
            }
        }
        return true
    }

    private fun setScaleFactor(view: View, scaleFactor: Float) {
        view.scaleX = scaleFactor
        view.scaleY = scaleFactor
    }
}