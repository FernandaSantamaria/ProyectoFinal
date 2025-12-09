package com.example.proyectofinal.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen() {
    Scaffold(
        bottomBar = { BottomNavBar(selectedIndex = 3) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F5F5))
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

@Composable
fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(Icons.Default.ArrowBack, contentDescription = null)
        Icon(Icons.Default.MoreVert, contentDescription = null)
    }
}

@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.height(12.dp).width(12.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Anna Adams", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text("@anna.adams09", fontSize = 14.sp, color = Color.Gray)
            Text("San Jose, CA", fontSize = 14.sp, color = Color.Gray)
        }
    }
}

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

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(label, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ActionButtons() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = {},
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6C63FF))
        ) {
            Text("Follow")
        }
        Button(
            onClick = {},
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF64B5F6))
        ) {
            Text("Message")
        }
    }
}

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
                color = if (selectedTab == index) Color(0xFF6C63FF) else Color.Gray,
                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

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

enum class GridSize { Small, Large }
