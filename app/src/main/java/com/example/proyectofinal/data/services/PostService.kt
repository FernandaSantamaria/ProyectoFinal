package com.example.proyectofinal.data.services

import com.example.proyectofinal.domain.dtos.MessageResponse
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.dtos.PostListResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PostService {
    @GET("posts/")
    suspend fun getPosts(): PostListResponse

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): PostDTO

    @POST("posts/")
    suspend fun createPost(@Body body: Map<String, String>): PostDTO

    @POST("posts/{id}/like")
    suspend fun likePosts(@Path("id") id: Int): MessageResponse

    @DELETE("posts/{id}/like")
    suspend fun removeLike(@Path("id") id: Int): MessageResponse
}