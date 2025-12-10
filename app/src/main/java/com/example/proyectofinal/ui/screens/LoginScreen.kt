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
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import com.example.proyectofinal.domain.utils.Preferences
import com.example.proyectofinal.ui.components.LoadingOverlay
import com.example.proyectofinal.ui.viewmodels.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavHostController
) {
    val colors = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography

    val viewModel: AuthViewModel = viewModel()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var isLoggedIn by remember {
        mutableStateOf(Preferences.getIsLogged())
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn) {
            navController.navigate(HomeScreenRoute) {
                popUpTo(LoginScreenRoute) { inclusive = true }
            }
        }
    }


    fun handleLogin(isLoggedIn: Boolean, message: String) {
        if (isLoggedIn) {
            navController.navigate(HomeScreenRoute) {
                popUpTo(HomeScreenRoute)
            }
        } else {
            Toast.makeText(context, message, Toast.LENGTH_LONG)
                .show()
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
                text = "Zutto",
                fontWeight = FontWeight.ExtraBold,
                style = typography.displayLarge
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
                    imeAction = ImeAction.Done,
                    showKeyboardOnFocus = true,
                ),
                keyboardActions = KeyboardActions {
                    focusManager.clearFocus(force = true)

                    viewModel.login(
                        username,
                        password
                    ) { isLoggedIn, message ->
                        handleLogin(isLoggedIn, message)
                    }
                },
                singleLine = true,
                shape = CircleShape
            )

            Button(
                onClick = {
                    viewModel.login(
                        username,
                        password
                    ) { isLoggedIn, message ->
                        handleLogin(isLoggedIn, message)
                    }
                },
            ) {
                Text("Iniciar sesión")
            }
        }

        if (viewModel.isLoading) {
            LoadingOverlay()
        }
    }
}

