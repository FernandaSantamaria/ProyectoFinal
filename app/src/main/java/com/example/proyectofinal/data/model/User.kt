package com.example.proyectofinal.data.model

data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val avatar: String?,
    val createdAt: String,
    // Campos adicionales para la UI
    var isFollowing: Boolean = false,
    var postsCount: Int = 0,
    var followersCount: Int = 0,
    var followingCount: Int = 0
)