package com.example.newsapp.features.newslist

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

enum class NewsApp{
    NewsList,
    NewsOverview
}

@Composable
fun NewsListNavigation(
    viewModal: NewsListViewModal,
) {


    val navController = rememberNavController()

    val category by viewModal.selectedCategory.collectAsState()
    
    NavHost(navController = navController, startDestination = NewsApp.NewsList.name) {

        composable(route = NewsApp.NewsList.name) {
            NewsListScreen(
                newsViewModal = viewModal,
                onClicked = { selectedArticleId ->
                    navController.navigate("${NewsApp.NewsOverview.name}/$selectedArticleId")
                }
            )
        }

        composable(
            route = "${NewsApp.NewsOverview.name}/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 0
            NewsOverviewSreeen(
                viewModal = viewModal, articleId = articleId, category = category,
                onBackButtonClicked = {
                    navController.navigate(NewsApp.NewsList.name)
                }
            )
        }
    }
}