package com.tifone.android.learn.ui

import android.os.Bundle
import android.support.text.emoji.EmojiCompat
import android.support.text.emoji.FontRequestEmojiCompatConfig
import android.support.v4.provider.FontRequest
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.Magnifier
import com.tifone.android.learn.R

class EmojiDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emoji_demo_layout)
        val emojiBtn = findViewById<Button>(R.id.emoji_btn)
    }


}