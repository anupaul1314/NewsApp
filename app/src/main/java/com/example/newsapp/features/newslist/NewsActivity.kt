package com.example.newsapp.features.newslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsViewModal = ViewModelProvider.create(this).get(NewsListViewModal::class.java)
        setContent {
           NewsListNavigation(viewModal = newsViewModal)
        }
    }
}