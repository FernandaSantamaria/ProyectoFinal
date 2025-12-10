package com.example.proyectofinal.data.services

import com.example.proyectofinal.domain.dtos.CommentResponse
import com.example.proyectofinal.domain.dtos.MessageResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CommentService {
    @POST("comments/{id}/like")
    suspend fun likeComment(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): MessageResponse

    @DELETE("comments/{id}/like")
    suspend fun removeCommentLike(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): MessageResponse

    @PUT("comments/{id}")
    suspend fun updateComment(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): CommentResponse

    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): MessageResponse
}