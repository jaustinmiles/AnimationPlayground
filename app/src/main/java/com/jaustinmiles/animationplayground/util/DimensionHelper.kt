package com.jaustinmiles.animationplayground.util

import android.util.DisplayMetrics
import com.jaustinmiles.animationplayground.MainActivity

class DimensionHelper {
    companion object {
        fun getWidthAndHeight(activity: MainActivity): Pair<Int, Int> {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val height = displayMetrics.heightPixels
            val width = displayMetrics.widthPixels
            return Pair(height, width)
        }
    }

}