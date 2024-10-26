package com.example.newsapp.repository

import com.example.newsapp.data.modals.Articles
import com.example.newsapp.data.remote.NewsApi
import javax.inject.Inject

class Repository @Inject constructor(
    private val newsApi: NewsApi
){
    suspend fun getApiInstance(
        country: String,
        category: String,
        apiKey: String
    ): List<Articles> {
        //val response = newsApi.getNewsInstance(country,category,apiKey)
        var result: List<Articles> = emptyList()

//        if (response.isSuccessful && response.body() != null) {
//            result = response.body()!!.articles
//        }
        return result
    }
}