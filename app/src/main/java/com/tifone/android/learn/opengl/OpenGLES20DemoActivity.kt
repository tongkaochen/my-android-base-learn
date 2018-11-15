package com.tifone.android.learn.opengl

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle

class OpenGLES20DemoActivity: Activity() {
    private lateinit var mGLView: MyGLSurfaceView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGLView = MyGLSurfaceView(this)
        setContentView(mGLView)
    }
}