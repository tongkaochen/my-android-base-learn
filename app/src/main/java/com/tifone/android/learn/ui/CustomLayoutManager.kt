package com.tifone.android.learn.ui

import android.support.v7.widget.RecyclerView

class CustomLayoutManager : RecyclerView.LayoutManager() {
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        //TODO("not implemented")
        return RecyclerView.LayoutParams(-2, -2)
    }

}