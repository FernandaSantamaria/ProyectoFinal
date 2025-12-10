package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectofinal.ui.screens.HomeScreen
import com.example.proyectofinal.ui.screens.LoginScreen
import com.example.proyectofinal.ui.screens.PostDetailScreen
import com.example.proyectofinal.ui.screens.ProfileScreen
import com.example.proyectofinal.ui.screens.RegisterScreen
import com.example.proyectofinal.ui.screens.HomeScreenRoute
import com.example.proyectofinal.ui.screens.LoginScreenRoute
import com.example.proyectofinal.ui.screens.PostDetailScreenRoute
import com.example.proyectofinal.ui.screens.ProfileScreenRoute
import com.example.proyectofinal.ui.screens.RegisterScreenRoute
import com.example.proyectofinal.ui.theme.ProyectoFinalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalApp()
        }
    }
}

@Composable
fun ProyectoFinalApp() {
    ProyectoFinalTheme {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = LoginScreenRoute.route   // <- String
        ) {

            composable(LoginScreenRoute.route){
                LoginScreen(navController = navController)
            }
            composable(RegisterScreenRoute.route) {
                RegisterScreen(navController = navController)
            }
            composable(HomeScreenRoute.route) {
                HomeScreen(navController = navController)
            }
            composable(
                route = PostDetailScreenRoute.route,
                arguments = listOf(
                    navArgument("postId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getInt("postId") ?: 0
                PostDetailScreen(
                    navController = navController,
                    postId = postId
                )
            }
            composable(
                route = ProfileScreenRoute.route,
                arguments = listOf(
                    navArgument("userId") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                ProfileScreen(
                    navController = navController,
                    userId = userId
                )
            }
        }
    }
}
