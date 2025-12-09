package com.example.proyectofinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.model.Post
import com.example.proyectofinal.data.repository.InstagramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class HomeViewModel(
    private val repository: InstagramRepository = InstagramRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getPosts().fold(
                onSuccess = { posts ->
                    _uiState.value = _uiState.value.copy(
                        posts = posts,
                        isLoading = false
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Unknown error",
                        isLoading = false
                    )
                }
            )
        }
    }

    fun likePost(postId: Int) {
        viewModelScope.launch {
            val currentPosts = _uiState.value.posts
            val post = currentPosts.find { it.id == postId } ?: return@launch

            if (post.isLiked) {
                repository.unlikePost(postId)
            } else {
                repository.likePost(postId)
            }

            // Actualizar UI optimistamente
            val updatedPosts = currentPosts.map {
                if (it.id == postId) {
                    it.copy(
                        _isLiked = !it.isLiked,
                        _likesCount = if (it.isLiked) it.likesCount - 1 else it.likesCount + 1
                    )
                } else it
            }
            _uiState.value = _uiState.value.copy(posts = updatedPosts)
        }
    }
}