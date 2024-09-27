package com.example.newsapp.data.remote

import com.example.newsapp.data.modals.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApi {

    @GET("top-headlines")
    suspend fun getNewsInstance(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Response<News>
}