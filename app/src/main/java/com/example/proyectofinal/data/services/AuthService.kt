package com.example.proyectofinal.data.services

import com.example.proyectofinal.domain.dtos.AuthResponse
import com.example.proyectofinal.domain.dtos.Login
import com.example.proyectofinal.domain.dtos.Register
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/register")
    suspend fun register(@Body register: Register): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body login: Login): AuthResponse
}