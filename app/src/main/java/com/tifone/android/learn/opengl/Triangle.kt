package com.tifone.android.learn.opengl

import android.opengl.GLES20
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

const val COORDS_PRE_VERTEX = 3
var triangleCoords = floatArrayOf(
        0.0f, 0.6f, 0.0f,
        -0.5f, -0.3f, 0.0f,
        0.5f, -0.3f, 0.0f
)
class Triangle {
    private var mProgram: Int
    private val vertexShaderCode =
                    "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;\n" +
                    "void main() {\n" +
                    "    gl_Position = uMVPMatrix * vPosition;\n" +
                    "}\n"

    private val fragmentShaderCode =
                    "precision mediump float;\n" +
                    "uniform vec4 vColor;\n" +
                    "void main() {\n" +
                    "    gl_FragColor = vColor;\n" +
                    "}\n"

    val color = floatArrayOf(0.6f, 0.7f, 0.2f, 1.0f)
    private var vertexBuffer: FloatBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
        order(ByteOrder.nativeOrder())
        asFloatBuffer().apply {
            put(triangleCoords)
            position(0)
        }
    }
    init {
        val vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode)
        val fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode)
        mProgram = GLES20.glCreateProgram().also { program ->
            GLES20.glAttachShader(program, vertexShader)
            GLES20.glAttachShader(program, fragmentShader)
            GLES20.glLinkProgram(program)
        }

    }
    private fun loadShader(type: Int, code: String): Int {
        return GLES20.glCreateShader(type).also {shader ->
            GLES20.glShaderSource(shader, code)
            GLES20.glCompileShader(shader)

            //Log.e("tifone", GLES20.glGetProgramInfoLog(GLES20.glGetError()))
        }
    }

    private var mPositionHandler: Int = 0
    private var mColorHandle: Int = 0
    private var mMVPMatrixHandler = 0
    private val vertexCount: Int = triangleCoords.size  / COORDS_PRE_VERTEX
    private val vertexStride: Int =  COORDS_PRE_VERTEX * 4
    fun draw(mvpMatrix: FloatArray) {
        GLES20.glUseProgram(mProgram)
        mPositionHandler = GLES20.glGetAttribLocation(mProgram, "vPosition").also {
            GLES20.glEnableVertexAttribArray(it)
            GLES20.glVertexAttribPointer(
                    it,
                    COORDS_PRE_VERTEX,
                    GLES20.GL_FLOAT,
                    false,
                    vertexStride,
                    vertexBuffer
            )

            mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor").also { colorHandle ->
                GLES20.glUniform4fv(colorHandle, 1, color, 0)
            }

        }
        mMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix").also { mvpMatrixHandler ->
            GLES20.glUniformMatrix4fv(mvpMatrixHandler, 1, false, mvpMatrix, 0)
        }
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount)
        GLES20.glDisableVertexAttribArray(mPositionHandler)
    }
}