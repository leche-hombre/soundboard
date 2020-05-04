package com.example.soundboard

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout


// TODO: Make this extend view, then other view components can extend from this
// TODO: Create a layout that's like: [bin][timer][draggable button], holding the button scales it up and starts the time
class DraggableView : Activity() {

    private var xPositionDelta: Int = 0
    private var xOriginalPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.draggable_view)
        val btnDraggable = this.findViewById<Button>(R.id.btnDrag)
        val layoutParams: ConstraintLayout.LayoutParams = btnDraggable.layoutParams as ConstraintLayout.LayoutParams
        xOriginalPosition = layoutParams.leftMargin
        btnDraggable.setOnTouchListener {view, motionEvent -> onTouch(view, motionEvent) }
    }

    private fun onTouch(view: View, motionEvent: MotionEvent): Boolean {

        val xPosition: Int = motionEvent.rawX.toInt()
        val layoutParams: ConstraintLayout.LayoutParams = view.layoutParams as ConstraintLayout.LayoutParams

        when(motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                xPositionDelta = xPosition - layoutParams.leftMargin
                setScaleFactor(view, 1.2f)
            }
            MotionEvent.ACTION_UP -> {
                view.performClick()
                layoutParams.leftMargin = xOriginalPosition
                view.layoutParams = layoutParams
                setScaleFactor(view, 1f)
            }
            MotionEvent.ACTION_MOVE -> {
                if (xPosition - xPositionDelta < layoutParams.leftMargin) {
                    layoutParams.leftMargin = xPosition - xPositionDelta
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