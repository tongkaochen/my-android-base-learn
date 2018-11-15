package com.tifone.android.learn.opengl

import android.content.Context
import android.opengl.GLSurfaceView
import android.view.MotionEvent

private const val TOUCH_SCALE_FATOR = 180f / 320f
private var mPreviousX: Float = 0f
private var mPreviousY: Float = 0f

class MyGLSurfaceView(context: Context) : GLSurfaceView(context) {
    private val mRender: MyGLRender
    init {
        // create an OpenGL 2.o context
        setEGLContextClientVersion(2)
        mRender = MyGLRender()
        setRenderer(mRender)
        renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x: Float = event.x
        val y: Float = event.y
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                var dx: Float = x - mPreviousX
                var dy: Float = y - mPreviousY
                if (y  > height / 2) {
                    dx *= -1
                }
                if (x < width / 2) {
                    dy *= -1
                }
                mRender.mAngle += (dx + dy) * TOUCH_SCALE_FATOR
                requestRender()
            }

        }
        mPreviousX = x
        mPreviousY = y
        return true

    }
}
