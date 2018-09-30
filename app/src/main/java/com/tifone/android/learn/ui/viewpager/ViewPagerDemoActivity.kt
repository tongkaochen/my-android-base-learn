package com.tifone.android.learn.ui.viewpager

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.tifone.android.learn.R

class ViewPagerDemoActivity : AppCompatActivity() {

    companion object {
        const val NUM_PAGES = 5
    }

    private lateinit var mViewPager: ViewPager
    private lateinit var mAdapter: SlidingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_pager_demo_layout)
        mAdapter = SlidingPagerAdapter(supportFragmentManager)
        mViewPager = findViewById(R.id.view_pager_layout)
        mViewPager.apply {
            adapter = mAdapter
            setPageTransformer(true, ScreenSlidingPageAnimator())
        }
    }
    override fun onBackPressed() {
        if (mViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            mViewPager.currentItem--
        }
    }

    class SlidingPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getItem(position: Int): Fragment =
                ScreenSlidingPageFragment.createInstance(position)

        override fun getCount(): Int = NUM_PAGES

    }
}