package com.tifone.android.learn

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.tifone.android.learn.ui.UiDemoActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.demo_ui).setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.demo_ui -> {
                startDemoActivity(UiDemoActivity::class.java)
            }
        }
    }
    private fun startDemoActivity(clz: Class<*>) {
        val intent = Intent(this, clz)
        startActivity(intent)
    }
}
