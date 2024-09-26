package com.example.newsapp.features.newslist

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.modals.Articles
import com.example.newsapp.repository.Repository
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModal @Inject constructor(private val repository: Repository) :ViewModel() {

    val newsList = MutableStateFlow<List<Articles>> (emptyList())

    private val country = Constants.country
    private val category = Constants.category
    private val apiKey = Constants.apiKey


    suspend fun getNewsList() {
        newsList.value = repository.getApiInstance()
    }
}