package com.example.newsapp.features

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.features.newslist.NewsOverviewSreeen
import com.example.newsapp.features.newslist.NewsListScreen
import com.example.newsapp.features.newslist.NewsViewModal

enum class NewsApp{
    NewsList,
    NewsOverview
}

@Composable
fun AppNavigation(
    viewModal: NewsViewModal
) {
    val navController = rememberNavController()
    
    NavHost(navController = navController, startDestination = NewsApp.NewsList.name) {

        composable(route = NewsApp.NewsList.name) {
            NewsListScreen(
                viewModal = viewModal,
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
            NewsOverviewSreeen(viewModal = viewModal, articleId = articleId)
        }
    }
}