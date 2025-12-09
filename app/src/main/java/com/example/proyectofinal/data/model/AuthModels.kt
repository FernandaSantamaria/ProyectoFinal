package com.example.proyectofinal.data.model


// Requests
data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String,
    val avatar: String? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class CreatePostRequest(
    val caption: String?,
    val imageUrl: String?
)

data class UpdatePostRequest(
    val caption: String?,
    val imageUrl: String?
)

data class CreateCommentRequest(
    val content: String
)

data class UpdateCommentRequest(
    val content: String
)

// Responses
data class AuthResponse(
    val token: String,
    val user: User
)

data class UserProfileResponse(
    val user: User,
    val posts: List<Post>
)

data class MessageResponse(
    val message: String
)