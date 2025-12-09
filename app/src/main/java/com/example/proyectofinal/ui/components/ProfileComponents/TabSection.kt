package com.example.proyectofinal.ui.components.ProfileComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.proyectofinal.ui.theme.ItemPurple

@Composable
fun TabSection() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Photo", "Video", "About", "Favorite")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, tab ->
            Text(
                text = tab,
                modifier = Modifier.clickable { selectedTab = index },
                color = if (selectedTab == index) ItemPurple else Color.Gray,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}