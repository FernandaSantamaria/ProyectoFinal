package com.example.proyectofinal.data.services

import com.example.proyectofinal.domain.dtos.CommentDTO
import com.example.proyectofinal.domain.dtos.CommentResponse
import com.example.proyectofinal.domain.dtos.MessageResponse
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.dtos.PostListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PostService {
    @GET("posts/")
    suspend fun getPosts(): PostListResponse

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDTO

    @POST("posts/")
    suspend fun createPost(
        @Header("Authorization") authToken: String,
        @Body body: Map<String, String>
    ): PostDTO

    @POST("posts/{id}/like")
    suspend fun likePosts(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): MessageResponse

    @DELETE("posts/{id}/like")
    suspend fun removeLike(
        @Header("Authorization") authToken: String,
        @Path("id") id: Int
    ): MessageResponse

    @POST("posts/{id}/comment")
    suspend fun createComment(
        @Path("id") id: Int,
        @Body comment: CommentDTO
    ): CommentResponse

    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body body: Map<String, String>
    ): PostDTO

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): MessageResponse
}