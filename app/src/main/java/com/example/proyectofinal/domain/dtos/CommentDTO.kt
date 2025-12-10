package com.example.proyectofinal.domain.dtos

data class CommentDTO(
    val id: Int,
    val content: String,
    val user: UserDTO,
    val CommentLikes: List<UserDTO>
)
