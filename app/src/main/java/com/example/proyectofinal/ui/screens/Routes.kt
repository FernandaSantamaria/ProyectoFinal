package com.example.proyectofinal.ui.screens

import kotlinx.serialization.Serializable

// Si quieres puedes dejar el @Serializable, pero no es necesario
@Serializable
object LoginScreenRoute;

@Serializable
object RegisterScreenRoute;

@Serializable
object HomeScreenRoute;

@Serializable
data class PostDetailScreenRoute(val postId: Int);

@Serializable
data class ProfileScreenRoute(val userId: Int);
