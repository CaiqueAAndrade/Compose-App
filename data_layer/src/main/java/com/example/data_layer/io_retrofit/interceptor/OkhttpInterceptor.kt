package com.example.data_layer.io_retrofit.interceptor

import com.example.data_layer.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor

val interceptor = HttpLoggingInterceptor().apply {
    level = if (BuildConfig.DEBUG)
        HttpLoggingInterceptor.Level.BODY
    else HttpLoggingInterceptor.Level.NONE
}