package com.example.proyectofinal.data.model

data class Comment(
    val id: Int,
    val content: String,
    val userId: Int,
    val postId: Int,
    val createdAt: String,
    val updatedAt: String,
    val User: User? = null,
    val CommentLikes: List<User>? = null
) {
    val likesCount: Int
        get() = CommentLikes?.size ?: 0

    val user: User
        get() = User ?: com.example.proyectofinal.data.model.User(
            id = userId,
            name = "Unknown",
            username = "unknown",
            email = "",
            avatar = null,
            createdAt = ""
        )
}