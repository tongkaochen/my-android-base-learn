package com.tifone.android.learn.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.tifone.android.learn.R

class UiDemoActivity : AppCompatActivity() {

    private lateinit var mLayoutManager: RecyclerView.LayoutManager
    private lateinit var mAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_demo_layout)
        mLayoutManager = LinearLayoutManager(this)
        val dataSet = arrayOf("a", "b", "c", "d")
        mAdapter = MyAdapter(dataSet)
        findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    class MyAdapter(private val mDataSet: Array<String>) :
            RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): MyViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemCount() = mDataSet.size

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.textView.text = mDataSet[position]
        }

        /*        class MyViewHolder : RecyclerView.ViewHolder {
                    var textView : TextView
                    constructor(view: View) : super(view) {
                        textView = view.findViewById(R.id.content_item)
                    }

                }*/
        class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // !! meaning not null
            val textView = view.findViewById<TextView>(R.id.content_item)!!
        }

    }
}