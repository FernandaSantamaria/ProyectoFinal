package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.RetrofitClient
import com.example.proyectofinal.domain.dtos.UserProfileResponse
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {
    fun getUserProfile(
        id: Int,
        onResult: (Boolean, UserProfileResponse?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createUserService()
                val result = service.getUserProfile(id)

                if (result != null) {
                    // Perfil encontrado
                    onResult(true, result)
                } else {
                    // No se encontró el usuario
                    onResult(false, null)
                }
            } catch (e: Exception) {
                println(e)
                onResult(false, null)
            }
        }
    }
}