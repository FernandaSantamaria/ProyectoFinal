package com.example.proyectofinal.domain.dtos

data class PostDTO(
    val id: Int,
    val caption: String?,
    val imageUrl: String?,
    val user: UserDTO,
    val Likes: List<UserDTO>
)
