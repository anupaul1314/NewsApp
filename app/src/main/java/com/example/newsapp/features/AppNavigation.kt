package com.example.newsapp.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.features.newslist.FavoritesNews
import com.example.newsapp.features.newslist.NewsOverviewSreeen
import com.example.newsapp.features.newslist.NewsListScreen
import com.example.newsapp.features.newslist.NewsViewModal

enum class NewsApp{
    NewsList,
    NewsOverview,
    Favorites
}

@Composable
fun AppNavigation(
    viewModal: NewsViewModal
) {
    val navController = rememberNavController()

    val category by viewModal.selectedCategory.collectAsState()
    
    NavHost(navController = navController, startDestination = NewsApp.NewsList.name) {

        composable(route = NewsApp.NewsList.name) {
            NewsListScreen(
                viewModal = viewModal,
                onClicked = { selectedArticleId ->
                    navController.navigate("${NewsApp.NewsOverview.name}/$selectedArticleId")
                },
                onIconClicked = {
                    navController.navigate(NewsApp.Favorites.name)
                }
            )
        }

        composable(
            route = "${NewsApp.NewsOverview.name}/{articleId}",
            arguments = listOf(navArgument("articleId") { type = NavType.IntType })
        ) { backStackEntry ->
            val articleId = backStackEntry.arguments?.getInt("articleId") ?: 0
            NewsOverviewSreeen(viewModal = viewModal, articleId = articleId, category = category)
        }

        composable(route = NewsApp.Favorites.name) {
            FavoritesNews()
        }
    }
}