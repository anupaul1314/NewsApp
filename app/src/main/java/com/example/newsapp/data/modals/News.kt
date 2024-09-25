package com.example.newsapp.data.modals

data class News(
    val status: String,
    val totalResults: String,
    val articles: List<Articles>
)
