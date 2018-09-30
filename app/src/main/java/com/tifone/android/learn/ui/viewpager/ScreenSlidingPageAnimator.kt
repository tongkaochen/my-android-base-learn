package com.tifone.android.learn.ui.viewpager

import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View

class ScreenSlidingPageAnimator : ViewPager.PageTransformer {
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f
    private val MIN_ANGLE_SCALE = 0.75f
    override fun transformPage(view: View, position: Float) {
        Log.e("tifone", "view = ${view.tag} position  = $position")
        val pageWidth = view.width
        val pageHeight = view.height

        when {
            (position < -1) -> {
                // The page is way off-screen to left
                view.alpha = 0f
            }
            (position <= 1) -> {
                val scaleFactor: Float= Math.max(MIN_SCALE, 1 - Math.abs(position))
                val vertMargin: Float = pageHeight * (1 - scaleFactor) / 2
                val horzMargin: Float = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = -horzMargin / 2
                } else {
                    view.translationX = horzMargin / 2
                }
                // scale the page down
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
                view.alpha = MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA)
            }
            else -> {
                // The page is way off-screen to the right
                view.alpha = 0f
            }
        }
    }
}