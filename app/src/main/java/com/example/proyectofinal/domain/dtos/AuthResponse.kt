package com.example.proyectofinal.domain.dtos

data class AuthResponse(
    val user: UserDTO,
    val token: String
)
