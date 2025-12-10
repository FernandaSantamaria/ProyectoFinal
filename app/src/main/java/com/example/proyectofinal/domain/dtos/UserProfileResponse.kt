package com.example.proyectofinal.domain.dtos

data class UserProfileResponse(
    val user: UserDTO,
    val posts: List<PostDTO>
)
