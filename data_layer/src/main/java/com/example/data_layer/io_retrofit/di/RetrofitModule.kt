package com.example.data_layer.io_retrofit.di

import com.example.data_layer.BuildConfig
import com.example.data_layer.io_retrofit.api.UnsplashApi
import com.example.data_layer.io_retrofit.interceptor.interceptor
import com.example.data_layer.io_retrofit.repository.UnsplashRepository
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val repositoryModule = module {
    single { UnsplashRepository(get()) }
}

val retrofitModule = module {
    single {
        Gson()
    }

    single {
        interceptor
    }

    single(named("unsplash_http")) {
        OkHttpClient.Builder()
            .addInterceptor(get() as HttpLoggingInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single(named("unsplash_rf")) {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl(BuildConfig.UNSPLASH_URL)
            .client(get(named("unsplash_http")) as OkHttpClient)
            .build()
    }

    single {
        (get(named("unsplash_rf")) as Retrofit).create(UnsplashApi::class.java)
    }

    single {
        UnsplashRepository(unsplashApi = get())
    }
}