package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.proyectofinal.data.model.Post
import com.example.proyectofinal.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProfile: (Int) -> Unit,
    onNavigateToPostDetail: (Int) -> Unit,
    viewModel: HomeViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Instagram",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(Icons.Outlined.FavoriteBorder, "Notifications")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadPosts() }) {
                            Text("Reintentar")
                        }
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    items(uiState.posts) { post ->
                        PostItem(
                            post = post,
                            onLikeClick = { viewModel.likePost(post.id) },
                            onCommentClick = { onNavigateToPostDetail(post.id) },
                            onShareClick = { },
                            onUserClick = { onNavigateToProfile(post.userId) }
                        )
                        Divider(thickness = 1.dp, color = Color(0xFFDBDBDB))
                    }
                }
            }
        }
    }
}

@Composable
fun PostItem(
    post: Post,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit,
    onUserClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Post Header
        PostHeader(
            post = post,
            onUserClick = onUserClick
        )

        // Post Image
        if (!post.imageUrl.isNullOrEmpty()) {
            AsyncImage(
                model = post.imageUrl,
                contentDescription = "Post image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }

        // Post Actions
        PostActions(
            isLiked = post.isLiked,
            onLikeClick = onLikeClick,
            onCommentClick = onCommentClick,
            onShareClick = onShareClick
        )

        // Post Info
        PostInfo(post = post)
    }
}

@Composable
fun PostHeader(
    post: Post,
    onUserClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = post.user.avatar ?: "https://i.pravatar.cc/150?img=${post.userId}",
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .clickable { onUserClick() },
            contentScale = ContentScale.Crop
        )

        Text(
            text = post.user.username,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 10.dp)
                .weight(1f)
                .clickable { onUserClick() },
            fontSize = 14.sp
        )

        IconButton(onClick = { }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "More options",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PostActions(
    isLiked: Boolean,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Like",
                tint = if (isLiked) Color.Red else Color.Black,
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(onClick = onCommentClick) {
            Icon(
                imageVector = Icons.Outlined.ChatBubbleOutline,
                contentDescription = "Comment",
                modifier = Modifier.size(28.dp)
            )
        }

        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Outlined.Send,
                contentDescription = "Share",
                modifier = Modifier.size(26.dp)
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { }) {
            Icon(
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "Save",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun PostInfo(post: Post) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
    ) {
        // Likes count
        Text(
            text = "${post.likesCount} likes",
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )

        // Caption
        if (!post.caption.isNullOrEmpty()) {
            Row(modifier = Modifier.padding(top = 4.dp)) {
                Text(
                    text = post.user.username,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp
                )
                Text(
                    text = " ${post.caption}",
                    fontSize = 14.sp
                )
            }
        }

        // View comments
        if (post.commentsCount > 0) {
            Text(
                text = "View all ${post.commentsCount} comments",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { }
            )
        }

        // Time ago
        Text(
            text = post.timeAgo,
            color = Color.Gray,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}