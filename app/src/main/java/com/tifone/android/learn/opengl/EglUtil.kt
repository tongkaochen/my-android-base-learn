package com.tifone.android.learn.opengl

import android.graphics.SurfaceTexture
import android.opengl.*

class EglUtil(surfaceTexture: SurfaceTexture) {

    private lateinit var mDisplay: EGLDisplay
    private lateinit var mChosenConfig: Array<EGLConfig>
    private var mSurface: EGLSurface? = EGL14.EGL_NO_SURFACE
    private var mEGLContext: EGLContext? = EGL14.EGL_NO_CONTEXT
    private val mSurfaceTexture = surfaceTexture
    private val EGL_CONFIG_LIST: IntArray = intArrayOf(
            EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
            EGL14.EGL_RED_SIZE, 8,
            EGL14.EGL_GREEN_SIZE, 8,
            EGL14.EGL_BLUE_SIZE, 8,
            EGL14.EGL_ALPHA_SIZE, 8,
            EGL14.EGL_DEPTH_SIZE, 0,
            EGL14.EGL_STENCIL_SIZE, 0,
            EGL14.EGL_NONE
    )
    private val EGL_ATTRIBUTE_LIST: IntArray = intArrayOf(
            EGL14.EGL_RENDER_BUFFER, EGL14.EGL_BACK_BUFFER,
            EGL14.EGL_NONE
    )

    fun initGLWindow() {
        // step1
        mDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY)
        if(mDisplay == EGL14.EGL_NO_DISPLAY) {
            // get display error
        }
        // step2
        val version = intArrayOf(2)
        if (!EGL14.eglInitialize(mDisplay, version, 0, version, 1)) {
            // init egl error
        }
        // step3
        val numConfigs = intArrayOf(1)
        mChosenConfig = arrayOf()
        if (!EGL14.eglChooseConfig(mDisplay, EGL_CONFIG_LIST,
                        0, mChosenConfig, 0, 1, numConfigs, 0)) {
            // choose config error
        }

        // step4
        mSurface = EGL14.eglCreateWindowSurface(
                mDisplay,
                mChosenConfig[1],
                mSurfaceTexture,
                EGL_ATTRIBUTE_LIST,
                0)
        if (mSurface == null || mSurface == EGL14.EGL_NO_SURFACE) {
            // surface create fail
        }

        // step5
        val EGL_CONTEXT_CONFIG = intArrayOf(
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 3,
                EGL14.EGL_NONE
        )
        mEGLContext = EGL14.eglCreateContext(
                mDisplay,
                mChosenConfig[0],
                EGL14.EGL_NO_CONTEXT,
                EGL_CONTEXT_CONFIG,
                0)
        if (null == mEGLContext || EGL14.EGL_NO_CONTEXT == mEGLContext) {
            // context create fail
        }

        // step6
        if (!EGL14.eglMakeCurrent(mDisplay, mSurface, mSurface, mEGLContext)) {
            // make current context fail
        }
    }
}