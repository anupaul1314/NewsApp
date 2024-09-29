package com.example.newsapp.features.newslist


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.modals.Articles
import com.example.newsapp.repository.Repository
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsViewModal @Inject constructor(private val repository: Repository) :ViewModel() {

    val newsList = MutableStateFlow<List<Articles>> (emptyList())

    private val country = Constants.country
    private val category = Constants.category
    private val apiKey = Constants.apiKey


     suspend fun getNewsListByCategory(category: String) {
        newsList.value = repository.getApiInstance(country,category,apiKey)
    }

    private val _selectedCategory = MutableStateFlow("Science")
    val selectedCategory: StateFlow<String> = _selectedCategory

    suspend fun updateSelectedCategory(category: String) {
        _selectedCategory.value = category
        getNewsListByCategory(category)
    }
}