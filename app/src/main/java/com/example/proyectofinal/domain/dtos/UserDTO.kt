package com.example.proyectofinal.domain.dtos

data class UserDTO(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val avatar: String?
)
