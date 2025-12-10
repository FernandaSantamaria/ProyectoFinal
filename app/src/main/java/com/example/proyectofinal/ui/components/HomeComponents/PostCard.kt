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
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.utils.Preferences
import com.example.proyectofinal.ui.viewmodels.PostViewModel
import java.sql.Timestamp
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun PostCard(post: PostDTO) {
    val viewModel: PostViewModel = viewModel()
    val context = LocalContext.current

    val currentUserId = Preferences.getUserId()
    val postBelongsToUser = currentUserId == post.User.id

    var numberOfLikes by remember {
        mutableStateOf(post.Likes.size)
    }

    var hasLikeFromUser by remember {
        mutableStateOf(post.Likes.any { user -> user.id == currentUserId })
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
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
                        if (post.User.avatar != null) {
                            AsyncImage(
                                model = post.User.avatar,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "@${post.User.username}",
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${post.createdAt.toDateString()}${
                                if (
                                    post.createdAt.equals(post.updatedAt)
                                ) "" else " (editado el ${post.updatedAt.toDateString()})"
                            }",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                if (postBelongsToUser) {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                    }
                }
            }


            if (post.caption != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(post.caption)
            }

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
                        viewModel.removeLike(post.id) { isSuccess, message ->
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
                        viewModel.likePost(post.id) { isSuccess, message ->
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

                ActionButton(
                    icon = Icons.Default.ChatBubbleOutline
                ) {}
            }
        }
    }
}

fun Timestamp.toDateString(): String {
    val instant = this.toInstant()
    val zoneId = ZoneId.systemDefault()
    val zonedDateTime = instant.atZone(zoneId)
    val formatter = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.MEDIUM)
        .withZone(zoneId)
    return zonedDateTime.format(formatter)
}