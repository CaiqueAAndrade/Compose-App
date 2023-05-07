package com.example.data_layer.io_retrofit.api

import com.example.data_layer.io_retrofit.view_data.Photo
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {

    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String
    ): List<Photo>

    @GET("/search/photos")
    suspend fun searchPhotos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String,
        @Query("query") query: String
    ): List<Photo>
}