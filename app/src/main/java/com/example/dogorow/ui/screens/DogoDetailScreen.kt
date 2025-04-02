package com.example.dogorow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogoDetailScreen(
    dogoId: Int,
    viewModel: DogoViewModel,
    onBackClick: () -> Unit
) {
    val dogo = viewModel.getDogo(dogoId)

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Detale") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Delete functionality */ }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (dogo != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Large dog image/avatar
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .background(dogo.color, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🐾", style = MaterialTheme.typography.displayLarge)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Dog name and breed
                Text(
                    text = dogo.name,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = dogo.breed,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Like button
                Button(
                    onClick = { viewModel.toggleLike(dogoId) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (dogo.isLiked) Color(0xFFE91E63) else MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = if (dogo.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Like"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (dogo.isLiked) "Lubisz to!" else "Polub")
                }
            }
        } else {
            // Error state if dog not found
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Nie znaleziono pieska :(")
            }
        }
    }
}