package com.example.proyectofinal.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.dtos.UserDTO
import com.example.proyectofinal.domain.utils.Preferences
import com.example.proyectofinal.ui.components.HomeComponents.CommentCard
import com.example.proyectofinal.ui.components.HomeComponents.CreateCommentCard
import com.example.proyectofinal.ui.components.HomeComponents.PostCard
import com.example.proyectofinal.ui.theme.BackgroundWhite
import com.example.proyectofinal.ui.viewmodels.PostViewModel
import com.example.proyectofinal.ui.viewmodels.UserViewModel

@Composable
fun PostDetailScreen(
    navController: NavHostController,
    postId: Int,
) {
    val colors = MaterialTheme.colorScheme

    val viewModel: PostViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()
    val context = LocalContext.current

    var commentText by remember {
        mutableStateOf("")
    }

    var post by remember {
        mutableStateOf<PostDTO?>(null)
    }

    var user by remember {
        mutableStateOf<UserDTO?>(null)
    }

    LaunchedEffect(true) {
        viewModel.getPostById(postId) { isSuccess, response ->
            if (!isSuccess) Toast.makeText(
                context,
                "Algo salió mal.",
                Toast.LENGTH_SHORT
            ).show()
            post = response
        }

        userViewModel.getUserProfile(Preferences.getUserId()) { isSuccess, response ->
            user = response?.user
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeContentPadding()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundWhite),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (post != null) {
                item {
                    PostCard(post!!) {}
                }

                item {
                    Text(
                        text = "Comentarios",
                        style = MaterialTheme.typography.titleLarge
                    )
                }

                items(post!!.Comments) { comment ->
                    CommentCard(comment)
                }

                item {
                    CreateCommentCard(
                        avatar = user?.avatar,
                        text = commentText,
                        onTextChange = { commentText = it }
                    ) {
                        viewModel.createComment(
                            postId = post!!.id,
                            content = commentText,
                        ) { isSuccess, message ->
                            if (isSuccess) {
                                Toast.makeText(
                                    context,
                                    "Comentario enviado",
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
            }
        }
    }
}
