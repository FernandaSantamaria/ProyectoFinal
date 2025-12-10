package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.RetrofitClient
import com.example.proyectofinal.domain.dtos.MessageResponse
import com.example.proyectofinal.domain.dtos.PostDTO
import com.example.proyectofinal.domain.dtos.PostListResponse
import com.example.proyectofinal.domain.utils.Preferences
import kotlinx.coroutines.launch

class PostViewModel : ViewModel() {
    fun getPosts(onResult: (Boolean, PostListResponse?) -> Unit) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createPostService()
                val result = service.getPosts()

                if (result.data.isNotEmpty()) {
                    onResult(true, result)
                } else {
                    onResult(false, null)
                }
            } catch (e: Exception) {
                println(e)
                onResult(false, null)
            }
        }
    }

    fun getPostById(
        id: Int,
        onResult: (Boolean, PostDTO?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createPostService()
                val result = service.getPostById(id)

                if (result != null) {
                    onResult(true, result)
                } else {
                    onResult(false, null)
                }
            } catch (e: Exception) {
                println(e)
                onResult(false, null)
            }
        }
    }

    fun createPost(
        caption: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createPostService()
                val body = mapOf(
                    "caption" to caption,
                )

                val result = service.createPost(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    body
                )

                if (result.id != 0) {
                    onResult(true, "Post creado")
                } else {
                    onResult(false, "No se pudo crear el post")
                }
            } catch (e: Exception) {
                println(e)
                onResult(false, "Error al crear el post")
            }
        }
    }

    fun likePost(
        postId: Int,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createPostService()
                val result: MessageResponse = service.likePosts(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    id = postId
                )

                onResult(true, result.message)

            } catch (e: Exception) {
                println(e)
                onResult(false, "Error al dar like")
            }
        }
    }

    fun removeLike(
        postId: Int,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createPostService()
                val result: MessageResponse = service.removeLike(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    id = postId
                )

                onResult(true, result.message)

            } catch (e: Exception) {
                println(e)
                onResult(false, "Error al quitar like")
            }
        }
    }
}