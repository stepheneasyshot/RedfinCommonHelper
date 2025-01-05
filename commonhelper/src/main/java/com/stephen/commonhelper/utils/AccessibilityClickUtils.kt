package com.stephen.commonhelper.utils

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityService.GestureResultCallback
import android.accessibilityservice.GestureDescription
import android.graphics.Path
import android.graphics.Rect
import android.os.Handler
import android.util.Log

/**
 * 扫描文字，点击扫描到的第index个，默认第一个
 */
fun AccessibilityService.scanAndClickByText(scanText: String, index: Int = 0) = try {
    infoLog("scanText:$scanText")
    rootInActiveWindow?.findAccessibilityNodeInfosByText(scanText)
        ?.get(index)?.apply {
            infoLog(this.text.toString())
            val rect = Rect()
            this.getBoundsInScreen(rect)
            val x = rect.centerX()
            val y = rect.centerY()
            infoLog("x:$x, y: $y")
            performClickByCoordinate(x.toFloat(), y.toFloat())
        }
} catch (e: Exception) {
    e.message?.let { errorLog(it) }
}

/**
 * 扫描控件id，点击扫描到的第index个，默认第一个
 */
fun AccessibilityService.scanAndClickById(viewId: String, index: Int = 0) = try {
    infoLog("scanViewId:$viewId")
    rootInActiveWindow?.findAccessibilityNodeInfosByViewId(viewId)
        ?.get(index)?.apply {
            infoLog(this.text.toString())
            val rect = Rect()
            this.getBoundsInScreen(rect)
            val x = rect.centerX()
            val y = rect.centerY()
            infoLog("x:$x, y: $y")
            performClickByCoordinate(x.toFloat(), y.toFloat())
        }
} catch (e: Exception) {
    e.message?.let { errorLog(it) }
}

/**
 * 辅助服务的模拟点击，根据屏幕x,y坐标值
 */
fun AccessibilityService.performClickByCoordinate(x: Float, y: Float) {
    Log.d("ClickUtils", "click: ($x, $y)")
    // 极短时长，即为点击
    startSwipeGesture(x, y, x, y, duration = 1L)
}

/**
 * 创建滑动手势
 * 使用：第二第三参数均可为空
 * dispatchGesture(@NonNull GestureDescription gesture,
 *             @Nullable GestureResultCallback callback,
 *             @Nullable Handler handler)
 *
 */
fun AccessibilityService.startSwipeGesture(
    startX: Float,
    startY: Float,
    endX: Float,
    endY: Float,
    duration: Long = 500L,
    callback: GestureResultCallback? = null,
    handler: Handler? = null
) {
    val path = Path()
    path.moveTo(startX, startY)
    path.lineTo(endX, endY)
    val builder = GestureDescription.Builder()
    // 立即开始
    val startTime = 0L
    // 滑动持续时间（单位：毫秒）
    val duration = duration
    val stroke = GestureDescription.StrokeDescription(path, startTime, duration)
    builder.addStroke(stroke)
    // 分发滑动手势
    dispatchGesture(builder.build(), callback, handler)
}