package com.example.proyectofinal.data

import com.example.proyectofinal.data.services.AuthService
import com.example.proyectofinal.data.services.CommentService
import com.example.proyectofinal.data.services.PostService
import com.example.proyectofinal.data.services.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    val httpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(40, TimeUnit.SECONDS)
            .readTimeout(40, TimeUnit.SECONDS)
            .writeTimeout(40, TimeUnit.SECONDS)
            .build()
    }

    val baseUrl = "http://10.0.2.2:3000/api/"

    val retrofit: Retrofit by lazy {
        retrofit2.Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createAuthService() : AuthService{
        return retrofit.create(AuthService::class.java)
    }

    fun createPostService() : PostService{
        return retrofit.create(PostService::class.java)
    }

    fun createCommentService() : CommentService{
        return retrofit.create(CommentService::class.java)
    }

    fun createUserService() : UserService{
        return retrofit.create(UserService::class.java)
    }
}