package com.tifone.android.learn

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.tifone.android.learn.drawable.DrawableDemoActivity
import com.tifone.android.learn.opengl.OpenGLES20DemoActivity
import com.tifone.android.learn.ui.EmojiDemoActivity
import com.tifone.android.learn.ui.NotificationDemoActivity
import com.tifone.android.learn.ui.UiDemoActivity
import com.tifone.android.learn.ui.viewpager.ViewPagerDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.demo_ui).setOnClickListener(this)
        findViewById<Button>(R.id.demo_emoji).setOnClickListener(this)
        findViewById<Button>(R.id.demo_notif).setOnClickListener(this)
        findViewById<Button>(R.id.demo_view_pager).setOnClickListener(this)
        findViewById<Button>(R.id.demo_drawable).setOnClickListener(this)
        findViewById<Button>(R.id.demo_opengl).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.demo_ui -> {
                startDemoActivity(UiDemoActivity::class.java)
            }
            R.id.demo_emoji -> {
                startDemoActivity(EmojiDemoActivity::class.java)
            }
            R.id.demo_notif -> {
                startDemoActivity(NotificationDemoActivity::class.java)
            }
            R.id.demo_view_pager -> {
                startDemoActivity(ViewPagerDemoActivity::class.java)
            }
            R.id.demo_drawable -> {
                startDemoActivity(DrawableDemoActivity::class.java)
            }
            R.id.demo_opengl -> {
                startDemoActivity(OpenGLES20DemoActivity::class.java)
            }
        }
    }


    private fun startDemoActivity(clz: Class<*>) {
        val intent = Intent(this, clz)
        startActivity(intent)
    }
}
