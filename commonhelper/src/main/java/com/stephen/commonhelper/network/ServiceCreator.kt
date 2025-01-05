package com.stephen.commonhelper.network

import com.stephen.commonhelper.utils.infoLog
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceCreator {

    private lateinit var _baseUrl: String

    fun setBaseUrl(baseUrl: String) {
        infoLog("setBaseUrl baseUrl: $baseUrl")
        _baseUrl = baseUrl
    }

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(_baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> getService(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> createService(): T = getService(T::class.java)
}