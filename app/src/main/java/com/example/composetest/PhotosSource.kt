package com.example.composetest

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data_layer.io_retrofit.repository.UnsplashRepository
import com.example.data_layer.io_retrofit.view_data.Photo

class PhotosSource(private val unsplashRepository: UnsplashRepository) :
    PagingSource<String, Photo>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Photo> {
        return try {
            val pageKey = params.key ?: "1"
            //Return when list end
            if (pageKey == "-1")
                return LoadResult.Error(ArrayIndexOutOfBoundsException())

            // Fetch data
            val response = unsplashRepository.getPhotos(page = pageKey.toInt())
            if (response.second != null) {
                LoadResult.Error(Exception(response.second))
            } else {
                LoadResult.Page(
                    data = response.first,
                    prevKey = params.key,
                    nextKey = (pageKey.toInt() + 1).toString()
                )
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Photo>): String? {
        return ""
    }
}