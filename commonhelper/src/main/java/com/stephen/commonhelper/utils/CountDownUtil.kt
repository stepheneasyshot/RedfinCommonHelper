package com.stephen.commonhelper.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.lang.RuntimeException

/**
 * 粗略的倒计时工具类，注意步进和总时长需要可配对整除
 *
 * Author:
 * Stephen
 *
 * Date:
 * 2023/12/14
 */
object CountDownUtil {

    private val coroutingMap = mutableMapOf<Int, CoroutineScope>()

    /**
     * totalTime总时长，单位s
     * interval步进，单位ms，默认值1000ms
     * onStart和onFinish可空
     */
    fun start(
        coroutineId: Int,
        totalTime: Long,
        interval: Long = 1000,
        onStart: () -> Unit = {},
        onTick: (currentTime: Int) -> Unit = {},
        onFinish: () -> Unit = {}
    ) {
        // 整除校验
        if ((totalTime * 1000 % interval).toInt() != 0) {
            throw IllegalArgumentException("CountDownUtil: remainder is not 0")
        }
        // 和id一起加入map，方便后续定点cancel
        // 如果你看过协程的官方文档或视频。你应该会知道Job和SupervisorJob的一个区别是，Job的子协程发生异常被取消会同时取消Job的其它子协程，而SupervisorJob不会。
        val countDownCoroutine = CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        )
        coroutingMap[coroutineId] = countDownCoroutine
        // 开始计时
        countDownCoroutine.launch(CoroutineExceptionHandler { _, e ->
            e.message?.let { errorLog(it) }
        }) {
            // 开始
            onStart()
            // 循环触发onTick
            repeat((totalTime * 1000 / interval).toInt()) {
                delay(interval)
                onTick((totalTime * 1000 / interval - (it + 1)).toInt())
            }
            // 循环结束，触发onFinish
            onFinish()
        }
    }

    fun isActive(coroutineId: Int): Boolean? {
        if (!coroutingMap.contains(coroutineId)) throw RuntimeException("Can't find your Id in the Coroutine map")
        return coroutingMap[coroutineId]?.isActive
    }

    /**
     * 以ID标识，取消协程，停止计时
     */
    fun cancel(coroutineId: Int) {
        if (!coroutingMap.contains(coroutineId)) throw RuntimeException("Can't find your Id in the Coroutine map")
        coroutingMap[coroutineId]?.cancel()
        coroutingMap.remove(coroutineId)
    }


}