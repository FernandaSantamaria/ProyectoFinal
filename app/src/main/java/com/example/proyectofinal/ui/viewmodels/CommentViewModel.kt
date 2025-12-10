package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.RetrofitClient
import com.example.proyectofinal.domain.utils.Preferences
import kotlinx.coroutines.launch

class CommentViewModel : ViewModel() {
    fun likeComment(
        commentId: Int,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createCommentService()
                val result = service.likeComment(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    id = commentId
                )
                // Si llega aquí, la petición salió bien
                onResult(true, result.message)
            } catch (e: Exception) {
                onResult(false, "Error al dar like al comentario")
                println(e.toString())
            }
        }
    }

    fun removeCommentLike(
        commentId: Int,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createCommentService()
                val result = service.removeCommentLike(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    id = commentId
                )
                onResult(true, result.message)
            } catch (e: Exception) {
                onResult(false, "Error al quitar like del comentario")
                println(e.toString())
            }
        }
    }

    fun updateComment(
        commentId: Int,
        content: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createCommentService()
                val body = mapOf("content" to content)
                service.updateComment(commentId, body)
                // Si tu CommentResponse tiene message, úsalo; si no, puedes poner uno fijo
                onResult(true, "Ok")
            } catch (e: Exception) {
                onResult(false, "Error al actualizar comentario")
                println(e.toString())
            }
        }
    }

    fun deleteComment(
        commentId: Int,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createCommentService()
                val result = service.deleteComment(
                    authToken = "Bearer " + Preferences.getAuthToken(),
                    id = commentId
                )
                onResult(true, result.message)
            } catch (e: Exception) {
                onResult(false, "Error al eliminar comentario")
                println(e.toString())
            }
        }
    }
}