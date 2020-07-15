package com.example.kidsdrawingapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import java.nio.file.Path

class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {
    private val drawPath: CustomPath? = null

    internal inner class CustomPath : Path() {

    }
}