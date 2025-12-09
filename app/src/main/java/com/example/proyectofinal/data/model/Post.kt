package com.example.proyectofinal.data.model
import com.google.gson.annotations.SerializedName

data class Post(
    val id: Int,
    val caption: String?,
    val imageUrl: String?,
    val userId: Int,
    val createdAt: String,
    val updatedAt: String,
    // Relaciones que vienen del backend
    val User: User? = null,
    val Comments: List<Comment>? = null,
    val Likes: List<User>? = null,
    // Campos calculados para la UI
    @SerializedName("likesCount")
    val _likesCount: Int? = null,
    @SerializedName("isLiked")
    val _isLiked: Boolean? = null
) {
    // Propiedades calculadas
    val likesCount: Int
        get() = _likesCount ?: Likes?.size ?: 0

    val isLiked: Boolean
        get() = _isLiked ?: false

    val commentsCount: Int
        get() = Comments?.size ?: 0

    val user: User
        get() = User ?: com.example.proyectofinal.data.model.User(
            id = userId,
            name = "Unknown",
            username = "unknown",
            email = "",
            avatar = null,
            createdAt = ""
        )

    val timeAgo: String
        get() = formatTimeAgo(createdAt)
}

fun formatTimeAgo(timestamp: String): String {
    return try {
        "Hace un momento"
    } catch (e: Exception) {
        timestamp
    }
}