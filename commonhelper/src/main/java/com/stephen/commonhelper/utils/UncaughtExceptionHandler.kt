package com.stephen.commonhelper.utils

import android.os.Process
import com.stephen.commonhelper.utils.infoLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

object UncaughtExceptionHandler : Thread.UncaughtExceptionHandler {

    fun init() {
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 未捕获的exception，走这个回调在这里处理兜底策略
     */
    override fun uncaughtException(t: Thread, e: Throwable) {
        infoLog("uncaughtException threadName: ${t.name} exception: ${e.message}")

        CoroutineScope(Dispatchers.IO).launch {
            infoLog("uncaughtException catched! 进行收尾工作")
            // 延时退出程序
            delay(3000L)
            Process.killProcess(Process.myPid())
            exitProcess(1)
        }
    }
}