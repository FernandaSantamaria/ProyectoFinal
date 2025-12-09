package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.ui.components.ProfileComponents.ActionButtons
import com.example.proyectofinal.ui.components.ProfileComponents.PhotoGrid
import com.example.proyectofinal.ui.components.ProfileComponents.ProfileHeader
import com.example.proyectofinal.ui.components.ProfileComponents.StatsRow
import com.example.proyectofinal.ui.components.ProfileComponents.TabSection
import com.example.proyectofinal.ui.components.ProfileComponents.TopBar
import com.example.proyectofinal.ui.theme.BackgroundWhite

@Composable
fun ProfileScreen() {
    Scaffold(
        bottomBar = { BottomNavBar(selectedIndex = 3) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BackgroundWhite)
        ) {
            TopBar()
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { ProfileHeader() }
                item { StatsRow() }
                item { ActionButtons() }
                item { TabSection() }
                item { PhotoGrid() }
            }
        }
    }
}

enum class GridSize { Small, Large }
