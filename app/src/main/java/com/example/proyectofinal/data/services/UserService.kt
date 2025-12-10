package com.example.proyectofinal.data.services

import com.example.proyectofinal.domain.dtos.UserProfileResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface UserService {
    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") id: Int): UserProfileResponse
}