package com.tifone.android.learn.ui.viewpager

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.tifone.android.learn.R

class ScreenSlidingPageFragment : Fragment() {
    private var mId: Int = 0
    companion object {
        private const val FRAGMENT_ID_KEY = "fragment_id_key"
        private val COLOR_LIST = arrayListOf(R.color.color_list_1, R.color.color_list_2,
                R.color.color_list_3, R.color.color_list_4, R.color.color_list_5)
        fun createInstance(id: Int): ScreenSlidingPageFragment {
            val bundle = Bundle()
            with(bundle){
                putInt(FRAGMENT_ID_KEY, id)
            }
            val fragment = ScreenSlidingPageFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            mId = getInt(FRAGMENT_ID_KEY, 0)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.screen_sliding_page_fragment_layout, container, false)
        val toastView = inflater.inflate(R.layout.custom_toast_layout, null)
        toastView.findViewById<TextView>(R.id.toast_text).apply {
            text = "$mId is clicked"
        }
        view.tag = mId
        view.setBackgroundColor(container!!.context.resources.getColor(COLOR_LIST[mId]))
        view.findViewById<TextView>(R.id.page_id_tv).apply {
            text = mId.toString()
            setOnClickListener {
                with(Toast(context)) {
                    setGravity(Gravity.BOTTOM, 0, 50)
                    duration = Toast.LENGTH_SHORT
                    this.view = toastView
                    show()
                }
                //Snackbar.make(toastView, "aa", Snackbar.LENGTH_SHORT).show()
            }
        }
        return view
    }
}