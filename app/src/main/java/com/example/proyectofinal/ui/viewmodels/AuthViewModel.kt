package com.example.proyectofinal.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.example.proyectofinal.data.RetrofitClient
import com.example.proyectofinal.domain.dtos.Login
import com.example.proyectofinal.domain.dtos.Register
import com.example.proyectofinal.domain.utils.Preferences
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {

    fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createAuthService()

                val registerBody = Register(
                    name = name,
                    username = username,
                    email = email,
                    password = password
                )

                val result = service.register(registerBody)

                if (result.user != null && result.token.isNotEmpty()){
                    Preferences.saveUserId(result.user.id)
                    Preferences.saveIsLogged(true)
                    onResult(true, "Registro exitoso")
                    println(result.toString())
                } else {
                    onResult(false, "Error al registrar")
                    println("No registrado")
                    println(result.toString())
                }
            } catch (e: HttpException) {
                onResult(false, "Error al registrar")
                println(e.toString())
            }
        }
    }

    fun login(
        username: String,
        password: String,
        onResult: (Boolean, String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val service = RetrofitClient.createAuthService()

                val loginBody = Login(
                    username = username,
                    password = password
                )

                val result = service.login(loginBody)

                if (result.user != null && result.token.isNotEmpty()){
                    Preferences.saveUserId(result.user.id)
                    Preferences.saveIsLogged(true)
                    onResult(true, "Login exitoso")
                } else {
                    onResult(false, "Credenciales incorrectas")
                }
            } catch (e: Exception) {
                onResult(false, "Error al iniciar sesión")
                println(e.toString())
            }
        }
    }
}