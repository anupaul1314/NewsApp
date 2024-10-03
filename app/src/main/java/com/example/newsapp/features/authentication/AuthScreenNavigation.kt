package com.example.newsapp.features.authentication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

enum class AuthScreen {
    SignIn,
    SignUp
}

@Composable
fun AuthScreenNavigation(
    authViewModal: AuthViewModal
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AuthScreen.SignIn.name
    ) {

        composable(route = AuthScreen.SignIn.name) {
            SignIn(authViewModal = authViewModal, onButtonClicked = {
                navController.navigate(AuthScreen.SignUp.name)
            }
            )
        }

        composable(route = AuthScreen.SignUp.name) {
            SignUp()
        }
    }
}