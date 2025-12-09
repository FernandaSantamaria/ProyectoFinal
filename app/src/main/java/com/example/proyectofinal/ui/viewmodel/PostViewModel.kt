package com.example.proyectofinal.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.model.Comment
import com.example.proyectofinal.data.model.Post
import com.example.proyectofinal.data.repository.InstagramRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PostUiState(
    val post: Post? = null,
    val comments: List<Comment> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isCreatingPost: Boolean = false,
    val postCreated: Boolean = false
)

class PostViewModel(
    private val repository: InstagramRepository = InstagramRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(PostUiState())
    val uiState: StateFlow<PostUiState> = _uiState.asStateFlow()

    fun loadPost(postId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            repository.getPostById(postId).fold(
                onSuccess = { post ->
                    _uiState.value = _uiState.value.copy(
                        post = post,
                        comments = post.Comments ?: emptyList(),
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

    fun createPost(caption: String?, imageUrl: String?) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isCreatingPost = true, error = null)

            repository.createPost(caption, imageUrl).fold(
                onSuccess = {
                    _uiState.value = _uiState.value.copy(
                        isCreatingPost = false,
                        postCreated = true
                    )
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Failed to create post",
                        isCreatingPost = false
                    )
                }
            )
        }
    }

    fun likePost(postId: Int) {
        viewModelScope.launch {
            val post = _uiState.value.post ?: return@launch

            if (post.isLiked) {
                repository.unlikePost(postId)
            } else {
                repository.likePost(postId)
            }

            _uiState.value = _uiState.value.copy(
                post = post.copy(
                    _isLiked = !post.isLiked,
                    _likesCount = if (post.isLiked) post.likesCount - 1 else post.likesCount + 1
                )
            )
        }
    }

    fun addComment(postId: Int, content: String) {
        viewModelScope.launch {
            repository.createComment(postId, content).fold(
                onSuccess = { comment ->
                    val currentComments = _uiState.value.comments.toMutableList()
                    currentComments.add(0, comment)
                    _uiState.value = _uiState.value.copy(comments = currentComments)
                },
                onFailure = { error ->
                    _uiState.value = _uiState.value.copy(
                        error = error.message ?: "Failed to add comment"
                    )
                }
            )
        }
    }
}