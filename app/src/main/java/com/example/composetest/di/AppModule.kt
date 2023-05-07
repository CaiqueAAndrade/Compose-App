package com.example.composetest.di

import com.example.composetest.PhotosViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { PhotosViewModel(get()) }
}