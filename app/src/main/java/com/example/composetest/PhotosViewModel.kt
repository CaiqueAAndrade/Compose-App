package com.example.composetest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data_layer.io_retrofit.repository.UnsplashRepository
import com.example.data_layer.io_retrofit.view_data.Photo
import kotlinx.coroutines.flow.Flow

class PhotosViewModel(private val unsplashRepository: UnsplashRepository) : ViewModel() {

    val photos: Flow<PagingData<Photo>> = Pager(PagingConfig(pageSize = 10)) {
        PhotosSource(unsplashRepository = unsplashRepository)
    }.flow

    private val _photo = MutableLiveData<Photo>()
    val photo: LiveData<Photo> = _photo

    fun setPhotoDetail(photo: Photo) {
        _photo.value = photo
    }
}