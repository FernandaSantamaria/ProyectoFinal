package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.proyectofinal.ui.components.HomeComponents.CreatePostCard
import com.example.proyectofinal.ui.components.HomeComponents.PostCard
import com.example.proyectofinal.ui.components.HomeComponents.TopSection
import com.example.proyectofinal.ui.theme.BackgroundWhite
import com.example.proyectofinal.ui.theme.ItemPurple
import com.example.proyectofinal.ui.viewmodels.PostViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    postViewModel: PostViewModel = viewModel()
) {
    var postText by remember { mutableStateOf("") }

    Scaffold(
        bottomBar = { BottomNavBar(selectedIndex = 0) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite),
            contentPadding = PaddingValues(16.dp)
        ) {
            item { TopSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { CreatePostCard(postText) { postText = it } }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item { PostCard() }
        }
    }
}

@Composable
fun BottomNavBar(selectedIndex: Int) {
    NavigationBar(containerColor = Color.White) {
        val items = listOf(
            Icons.Default.Home,
            Icons.Default.Search,
            Icons.Default.Notifications,
            Icons.Default.Person
        )
        items.forEachIndexed { index, icon ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = {},
                icon = { Icon(icon, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = ItemPurple,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}
