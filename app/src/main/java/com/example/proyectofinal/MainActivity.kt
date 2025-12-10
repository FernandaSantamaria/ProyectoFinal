package com.example.proyectofinal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import androidx.navigation.toRoute

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
            startDestination = LoginScreenRoute
        ) {
            composable<LoginScreenRoute> {
                LoginScreen(navController = navController)
            }
            composable<RegisterScreenRoute> {
                RegisterScreen(navController = navController)
            }
            composable<HomeScreenRoute> {
                HomeScreen(navController = navController)
            }
            composable<PostDetailScreenRoute> { backStack ->
                val args = backStack.toRoute<PostDetailScreenRoute>()
                PostDetailScreen(
                    navController = navController,
                    postId = args.postId
                )
            }
            composable<ProfileScreenRoute> { backStack ->
                val args = backStack.toRoute<ProfileScreenRoute>()
                ProfileScreen(
                    navController = navController,
                    userId = args.userId
                )
            }
        }
    }
}
