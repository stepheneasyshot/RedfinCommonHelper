package com.stephen.commonhelper.datastore

import android.content.Context
import android.content.SharedPreferences
import com.stephen.commonhelper.utils.infoLog


/**
 * SharedPreference存储工具类
 * 不会丢数据
 * 但是加回调不方便
 */
object SPHelper {

    private lateinit var share: SharedPreferences

    private lateinit var editor: SharedPreferences.Editor

    private const val SHARED_NAME = "SPHelper"

    fun init(context: Context) {
        share = context.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
        editor = share.edit();
    }

    // 采用同步保存，获取保存成功与失败的result
    fun putString(key: String, value: String?): Boolean {
        infoLog("putString key: $key, value: $value")
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(key: String): String? {
        val value = share.getString(key, null)
        infoLog("getString key: $key, value: $value")
        return value
    }

    fun getString(key: String, defaultValue: String): String {
        val value = share.getString(key, null)
        infoLog("getString key: $key, value: $value, defaultValue: $defaultValue")
        return value ?: defaultValue
    }

    fun putLong(key: String?, value: Long): Boolean {
        infoLog("putLong key: $key, value: $value")
        editor.putLong(key, value)
        return editor.commit()
    }

    fun putFloat(key: String?, value: Float): Boolean {
        infoLog("putFloat key: $key, value: $value")
        editor.putFloat(key, value)
        return editor.commit()
    }

    fun putInt(key: String?, value: Int): Boolean {
        infoLog("putInt key: $key, value: $value")
        editor.putInt(key, value)
        return editor.commit()
    }

    fun putBoolean(key: String?, value: Boolean): Boolean {
        infoLog("putBoolean key: $key, value: $value")
        editor.putBoolean(key, value)
        return editor.commit()
    }

    fun getLong(key: String?): Long {
        val value = share.getLong(key, -1)
        infoLog("getLong key: $key, value: $value")
        return value
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        val value = share.getInt(key, defaultValue)
        infoLog("getInt key: $key, value: $value")
        return value
    }

    fun getFloat(key: String?, defaultValue: Float): Float {
        val value = share.getFloat(key, defaultValue)
        infoLog("getFloat key: $key, value: $value")
        return value
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        val value = share.getBoolean(key, defaultValue)
        infoLog("getBoolean key: $key, value: $value")
        return value
    }

    fun removeSharedPreferenceByKey(key: String?): Boolean {
        infoLog("removeSharedPreferenceByKey key: $key")
        editor.remove(key)
        return editor.commit()
    }
}