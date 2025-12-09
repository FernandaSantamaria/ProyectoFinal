package com.example.proyectofinal.ui.components.ProfileComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.proyectofinal.ui.screens.GridSize

@Composable
fun PhotoGrid() {
    val gridItems = listOf(
        GridSize.Large, GridSize.Small,
        GridSize.Small, GridSize.Large
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PhotoGridItem(modifier = Modifier.weight(1f).height(250.dp))
            PhotoGridItem(modifier = Modifier.weight(1f).height(250.dp))
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PhotoGridItem(modifier = Modifier.weight(1f).height(200.dp))
            PhotoGridItem(modifier = Modifier.weight(1f).height(200.dp))
        }
    }
}

@Composable
fun PhotoGridItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color.LightGray)
    )
}