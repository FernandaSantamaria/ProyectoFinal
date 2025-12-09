package com.example.proyectofinal.data.network


import com.example.proyectofinal.data.model.*
import retrofit2.http.*

interface ApiService {

    // AUTH
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    // USERS
    @GET("users/{id}")
    suspend fun getUserProfile(@Path("id") userId: Int): UserProfileResponse

    // POSTS
    @GET("posts")
    suspend fun getPosts(): List<Post>

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") postId: Int): Post

    @POST("posts")
    suspend fun createPost(
        @Header("Authorization") token: String,
        @Body request: CreatePostRequest
    ): Post

    @PUT("posts/{id}")
    suspend fun updatePost(
        @Header("Authorization") token: String,
        @Path("id") postId: Int,
        @Body request: UpdatePostRequest
    ): Post

    @DELETE("posts/{id}")
    suspend fun deletePost(
        @Header("Authorization") token: String,
        @Path("id") postId: Int
    ): MessageResponse

    @POST("posts/{id}/like")
    suspend fun likePost(
        @Header("Authorization") token: String,
        @Path("id") postId: Int
    ): Post

    @DELETE("posts/{id}/like")
    suspend fun unlikePost(
        @Header("Authorization") token: String,
        @Path("id") postId: Int
    ): Post

    @POST("posts/{id}/comment")
    suspend fun createComment(
        @Header("Authorization") token: String,
        @Path("id") postId: Int,
        @Body request: CreateCommentRequest
    ): Comment

    // COMMENTS
    @PUT("comments/{id}")
    suspend fun updateComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int,
        @Body request: UpdateCommentRequest
    ): Comment

    @DELETE("comments/{id}")
    suspend fun deleteComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int
    ): MessageResponse

    @POST("comments/{id}/like")
    suspend fun likeComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int
    ): Comment

    @DELETE("comments/{id}/like")
    suspend fun unlikeComment(
        @Header("Authorization") token: String,
        @Path("id") commentId: Int
    ): Comment
}