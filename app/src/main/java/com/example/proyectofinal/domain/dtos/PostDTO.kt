package com.example.proyectofinal.domain.dtos

import java.sql.Timestamp

data class PostDTO(
    val id: Int,
    val caption: String?,
    val createdAt: Timestamp,
    val updatedAt: Timestamp,
    val User: UserDTO,
    val Likes: List<UserDTO>
)
