package com.example.proyectofinal.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.proyectofinal.ui.components.LoadingOverlay
import com.example.proyectofinal.ui.viewmodels.AuthViewModel

@Composable
fun RegisterScreen(
    navController: NavHostController,
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var username by remember {
        mutableStateOf("")
    }

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var isRegistered by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(isRegistered) {
        if (isRegistered) {
            navController.navigate(HomeScreenRoute) {
                popUpTo(RegisterScreenRoute) { inclusive = true }
            }
        }
    }


    fun handleRegister() {
        if (username.isBlank() || name.isBlank() || password.isBlank() || confirmPassword.isBlank()) return
        if (password != confirmPassword) return Toast.makeText(
            context,
            "Las contraseñas no coinciden",
            Toast.LENGTH_LONG
        )
            .show()

        viewModel.register(
            name,
            username,
            email,
            password
        ) { isRegistered, message ->
            if (isRegistered) {
                navController.navigate(HomeScreenRoute) {
                    popUpTo(RegisterScreenRoute) { inclusive = true }
                }
            } else {
                Toast.makeText(context, message, Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                12.dp,
                Alignment.CenterVertically
            )
        ) {
            Text(
                text = "Crear cuenta",
                fontWeight = FontWeight.Bold,
                style = typography.headlineLarge
            )

            // Nombre de usuario
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                modifier = Modifier
                    .width(320.dp),
                label = { Text("Nombre de usuario") },
                placeholder = { Text("ejemplo123") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = CircleShape
            )

            // Nombre completo
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .width(320.dp),
                label = { Text("Nombre completo") },
                placeholder = { Text("Adriel Ejemplo") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    autoCorrectEnabled = true,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = CircleShape
            )

            // Correo electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier
                    .width(320.dp),
                label = { Text("Correo electrónico") },
                placeholder = { Text("ejemplo123@correo.com") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = null,
                    )
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = CircleShape
            )

            // Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier
                    .width(320.dp),
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = null,
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.moveFocus(FocusDirection.Next)
                },
                singleLine = true,
                shape = CircleShape
            )

            // Contraseña
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier
                    .width(320.dp),
                label = { Text("Confirme su contraseña") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = null,
                    )
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus(force = true)
                    handleRegister()
                },
                singleLine = true,
                shape = CircleShape
            )

            Button(
                onClick = { handleRegister() },
            ) {
                Text("Crear cuenta")
            }

            TextButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Text("Iniciar sesión")
            }
        }

        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}