package com.example.weis.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {

    private lateinit var applicationContext: Context
    private val displayMetrics: DisplayMetrics by lazy {
        val windowManager = applicationContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        metrics
    }

    fun initScreenUtils(context: Context) {
        applicationContext = context.applicationContext
    }

    fun getScreenWidth(): Int {
        return displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return displayMetrics.heightPixels
    }
}
