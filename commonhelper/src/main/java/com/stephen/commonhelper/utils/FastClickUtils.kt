package com.stephen.commonhelper.utils

object FastClickUtils {

    private var lastClickTime = 0L

    private const val TIMEGAP = 2000L

    /**
     * 在时间gap内，只允许首次触发
     */
    fun isFastClick(): Boolean {
        val currentClickTime = System.currentTimeMillis()
        if ((currentClickTime - lastClickTime) < TIMEGAP) return true
        lastClickTime = currentClickTime
        return false
    }

}