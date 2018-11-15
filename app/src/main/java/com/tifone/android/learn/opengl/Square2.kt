package com.tifone.android.learn.opengl

import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.ShortBuffer

var squareCoords = floatArrayOf(
        -0.5f, 0.5f, 0f,
        -0.5f, -0.5f, 0f,
        0.5f, -0.5f, 0f,
        0.5f, 0.5f, 0f
)
class Square2 {
    private val order = shortArrayOf(0, 1, 2, 0, 2, 3)
    private val vertexBuffer:FloatBuffer = ByteBuffer.allocateDirect(squareCoords.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(squareCoords)
            position(0)
        }
    }
    private val drawListBuffer: ShortBuffer = ByteBuffer.allocateDirect(order.size * 2).run {
        order(ByteOrder.nativeOrder())
        asShortBuffer().apply {
            put(order)
            position(0)
        }
    }
}