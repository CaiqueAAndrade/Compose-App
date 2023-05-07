package com.example.data_layer.io_retrofit.view_data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Locale

data class Photo(
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    val color: String,
    val width: Int,
    val height: Int,
    val urls: Urls,
    val user: User,
    var favorite: Boolean? = false
) {
    val date: String
        get() = try {
            val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            SimpleDateFormat("MMM dd YYYY", Locale.getDefault()).format(parser.parse("2018-12-14T09:55:00")!!)
        } catch (e: Exception) {
            createdAt
        }
}

data class Urls(
    val raw: String
)

data class User(
    @SerializedName("username")
    val userName: String
)
