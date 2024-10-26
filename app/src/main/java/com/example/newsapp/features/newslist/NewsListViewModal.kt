package com.example.newsapp.features.newslist


import android.app.Activity
import android.content.Context
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.modals.Articles
import com.example.newsapp.localization.LocaleHelper
import com.example.newsapp.repository.Repository
import com.example.newsapp.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NewsListViewModal @Inject constructor(private val repository: Repository) :ViewModel() {

    private val country = Constants.country
    private val category = Constants.category
    private val apiKey = Constants.apiKey

    private val _newsList = MutableStateFlow<List<Articles>> (emptyList())
    val newsList: StateFlow<List<Articles>> = _newsList

    private val _filteredNewsList = MutableStateFlow<List<Articles>> (emptyList())
    val filteredNewsList: StateFlow<List<Articles>> = _filteredNewsList

    private val _selectedCategory = MutableStateFlow("Science")
    val selectedCategory: StateFlow<String> = _selectedCategory

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> = _searchText

     suspend fun getNewsList(selctedCategory: String) {
        _newsList.value = repository.getApiInstance(country,selctedCategory,apiKey)
         _filteredNewsList.value = _newsList.value
    }

    suspend fun updateSelectedCategory(category: String) {
        _selectedCategory.value = category
        getNewsList(category)
    }

    fun updateText(newText: String) {
        _searchText.value = newText
    }

    fun filteredNews(query: String) {
        if (query.isNotEmpty()) {
            _filteredNewsList.value = _newsList.value.filter {
                it.title.contains(query, true)
            }
        } else {
            _filteredNewsList.value = _newsList.value
        }
    }
}
