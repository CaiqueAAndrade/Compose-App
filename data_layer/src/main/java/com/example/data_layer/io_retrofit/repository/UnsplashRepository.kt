package com.example.data_layer.io_retrofit.repository

import com.example.data_layer.BuildConfig
import com.example.data_layer.io_retrofit.api.UnsplashApi
import com.example.data_layer.io_retrofit.view_data.Photo

class UnsplashRepository(private val unsplashApi: UnsplashApi) {

    suspend fun getPhotos(
        page: Int
    ): Pair<List<Photo>, String?> {
        return try {
            val result = unsplashApi.getPhotos(page, BuildConfig.UNSPLASH_API_KEY)
            if (result.isEmpty()) {
                Pair(listOf(), "No images found")
            } else {
                Pair(result, null)
            }
        } catch (e: Exception) {
            Pair(listOf(), e.localizedMessage)
        }
    }

    suspend fun searchPhotos(
        page: Int,
        query: String
    ): Pair<List<Photo>, String?> {
        return try {
            val result = unsplashApi.searchPhotos(page, query, BuildConfig.UNSPLASH_API_KEY)
            if (result.isEmpty()) {
                Pair(listOf(), "No images found")
            } else {
                Pair(result, null)
            }
        } catch (e: Exception) {
            Pair(listOf(), e.localizedMessage)
        }
    }
}