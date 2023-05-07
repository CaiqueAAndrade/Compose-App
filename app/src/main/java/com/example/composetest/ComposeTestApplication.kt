package com.example.composetest

import android.app.Application
import com.example.composetest.di.viewModelModule
import com.example.data_layer.io_retrofit.di.repositoryModule
import com.example.data_layer.io_retrofit.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ComposeTestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ComposeTestApplication)
            modules(
                retrofitModule,
                repositoryModule,
                viewModelModule
            )
        }
    }
}