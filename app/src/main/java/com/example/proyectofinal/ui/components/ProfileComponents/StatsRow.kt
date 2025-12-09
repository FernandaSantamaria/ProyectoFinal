package com.example.proyectofinal.ui.components.ProfileComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        StatItem("24K", "Fans")
        StatItem("582", "Following")
        StatItem("2129", "Posts")
        StatItem("91", "Goodies")
    }
}