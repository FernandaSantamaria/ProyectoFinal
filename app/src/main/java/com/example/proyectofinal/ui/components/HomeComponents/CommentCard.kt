package com.example.proyectofinal.ui.components.HomeComponents

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyectofinal.domain.dtos.CommentDTO
import com.example.proyectofinal.domain.utils.Preferences
import com.example.proyectofinal.ui.viewmodels.CommentViewModel


@Composable
fun CommentCard(comment: CommentDTO) {
    val viewModel: CommentViewModel = viewModel()
    val context = LocalContext.current

    val currentUserId = Preferences.getUserId()

    var numberOfLikes by remember {
        mutableStateOf(comment.CommentLikes.size)
    }

    var hasLikeFromUser by remember {
        mutableStateOf(comment.CommentLikes.any { user -> user.id == currentUserId })
    }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    ) {
                        if (comment.User.avatar != null) {
                            AsyncImage(
                                model = comment.User.avatar,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        "@${comment.User.username}",
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))
            Text(comment.content)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionButton(
                    icon = if (hasLikeFromUser) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    count = numberOfLikes.toString()
                ) {
                    if (hasLikeFromUser) {
                        viewModel.removeCommentLike(comment.id) { isSuccess, message ->
                            if (isSuccess) {
                                hasLikeFromUser = false
                                numberOfLikes -= 1
                            } else {
                                Toast.makeText(
                                    context,
                                    "Algo salió mal.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    } else {
                        viewModel.likeComment(comment.id) { isSuccess, message ->
                            if (isSuccess) {
                                hasLikeFromUser = true
                                numberOfLikes += 1
                            } else {
                                Toast.makeText(
                                    context,
                                    "Algo salió mal.",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }
        }
    }
}