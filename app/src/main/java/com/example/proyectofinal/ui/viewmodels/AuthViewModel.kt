package com.example.proyectofinal.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinal.data.RetrofitClient
import com.example.proyectofinal.domain.dtos.Login
import com.example.proyectofinal.domain.dtos.Register
import com.example.proyectofinal.domain.utils.Preferences
import kotlinx.coroutines.launch
import retrofit2.HttpException

class AuthViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)

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

                if (result.user != null && result.token.isNotEmpty()) {
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
                isLoading = true

                val service = RetrofitClient.createAuthService()

                val loginBody = Login(
                    username = username,
                    password = password
                )

                val result = service.login(loginBody)

                if (result.user != null && result.token.isNotEmpty()) {
                    Preferences.saveUserId(result.user.id)
                    Preferences.saveIsLogged(true)
                    onResult(true, "Login exitoso")
                } else {
                    onResult(false, "Credenciales incorrectas")
                }
            } catch (e: Exception) {
                Log.e("LoginScreen", e.toString())

                if (e is HttpException) {
                    when (e.code()) {
                        404 -> {
                            onResult(false, "Usuario no encontrado")
                        }

                        401 -> {
                            onResult(false, "Contraseña incorrecta")
                        }

                        else -> {
                            onResult(
                                false,
                                "Algo salió mal. Intenta de nuevo más tarde."
                            )
                        }
                    }
                } else {
                    onResult(
                        false,
                        "Algo salió mal. Intenta de nuevo más tarde."
                    )
                }
            } finally {
                isLoading = false
            }
        }
    }
}