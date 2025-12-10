package com.example.proyectofinal.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.dtos.UserDTO
import com.example.proyectofinal.domain.utils.Preferences
import com.example.proyectofinal.ui.components.HomeComponents.CreatePostCard
import com.example.proyectofinal.ui.components.HomeComponents.PostCard
import com.example.proyectofinal.ui.components.HomeComponents.TopSection
import com.example.proyectofinal.ui.theme.BackgroundWhite
import com.example.proyectofinal.ui.theme.ItemPurple
import com.example.proyectofinal.ui.viewmodels.PostViewModel
import com.example.proyectofinal.ui.viewmodels.UserViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: PostViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current

    var postText by remember { mutableStateOf("") }

    var posts by remember {
        mutableStateOf(emptyList<PostDTO>())
    }

    var user by remember {
        mutableStateOf<UserDTO?>(null)
    }

    LaunchedEffect(true) {
        viewModel.getPosts { isSuccess, response ->
            if (isSuccess) {
                posts = response!!.data
            } else {
                Toast.makeText(
                    context,
                    "Algo salió mal. Intenta de nuevo más tarde.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        userViewModel.getUserProfile(Preferences.getUserId()) { isSuccess, response ->
            user = response?.user
        }
    }

    Scaffold(
        bottomBar = { BottomNavBar(selectedIndex = 0) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { TopSection() }
            item {
                CreatePostCard(
                    avatar = user?.avatar,
                    text = postText,
                    onTextChange = { postText = it }
                ) {
                    viewModel.createPost(
                        caption = postText,
                    ) { isSuccess, message ->
                        postText = ""

                        if (isSuccess) {
                            Toast.makeText(
                                context,
                                "Publicación creada",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Algo salió mal.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
            items(posts) { post -> PostCard(post) }
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


