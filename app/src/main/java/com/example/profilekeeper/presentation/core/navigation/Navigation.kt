package com.example.profilekeeper.presentation.core.navigation

import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.profilekeeper.presentation.screens.register_user.RegisterScreen
import com.example.profilekeeper.presentation.screens.user_list.UsersListScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = Screens.RegistrationScreen
        ) {

            composable<Screens.RegistrationScreen> {
                RegisterScreen()
            }

            composable<Screens.UsersListScreen> {
                UsersListScreen()
            }
            
        }

    }
}