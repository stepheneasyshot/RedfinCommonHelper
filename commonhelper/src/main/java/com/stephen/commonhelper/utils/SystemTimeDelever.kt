package com.stephen.commonhelper.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

object SystemTimeDelever {

    private var listeners = mutableListOf<OnSystemTimeChangeListener>()

    private val timeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent == null) return
            val action = intent.action
            if (action.isNullOrEmpty()) return
            if (action == Intent.ACTION_TIME_TICK) {
                if (listeners.size > 0) {
                    listeners.forEach {
                        it.updateTimeUi(getCrruentTime())
                    }
                }
            }
        }
    }

    fun initTimeListen(context: Context) {
        context.registerReceiver(timeReceiver, IntentFilter(Intent.ACTION_TIME_TICK))
    }

    fun release(context: Context) {
        if (listeners.size > 0) {
            listeners.clear()
        }
        context.unregisterReceiver(timeReceiver)
    }

    fun registerTimeListen(listener: OnSystemTimeChangeListener): String {
        listeners.add(listener)
        return getCrruentTime()
    }

    interface OnSystemTimeChangeListener {
        fun updateTimeUi(time: String)
    }
}