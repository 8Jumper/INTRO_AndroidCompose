package com.example.dogorow

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogoListScreen(
    viewModel: DogoViewModel,
    onDogoClick: (Int) -> Unit,
    onSettingsClick: () -> Unit
) {
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var breed by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // Top App Bar with Title and Settings
        SmallTopAppBar(
            title = { Text("Doggos") },
            actions = {
                IconButton(onClick = onSettingsClick) {
                    Icon(Icons.Default.Settings, contentDescription = "Settings")
                }
            }
        )

        // Search bar
        SearchBar(
            viewModel = viewModel,
            onAddClick = { showDialog = true }
        )

        // Counters (Dogs and Likes)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Adb, contentDescription = null)
            Text(
                text = " ${viewModel.dogos.size}",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(Icons.Default.Favorite, contentDescription = null)
            Text(
                text = " ${viewModel.likedCount}",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        // List of dogs
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            items(viewModel.filteredDogos) { dogo ->
                DogoListItem(
                    dogo = dogo,
                    onClick = { onDogoClick(dogo.id) },
                    onLikeClick = { viewModel.toggleLike(dogo.id) },
                    onDeleteClick = { viewModel.deleteDogo(dogo.id) }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Dodaj psa") },
            text = {
                Column {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Imię psa") }
                    )
                    OutlinedTextField(
                        value = breed,
                        onValueChange = { breed = it },
                        label = { Text("Rasa psa") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (name.isNotBlank() && breed.isNotBlank()) {
                        viewModel.addDogo(name, breed)
                        name = ""
                        breed = ""
                        showDialog = false
                    }
                }) {
                    Text("Dodaj")
                }
            },
            dismissButton = {
                Button(onClick = { showDialog = false }) {
                    Text("Anuluj")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    viewModel: DogoViewModel,
    onAddClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.updateSearchQuery(it) },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Poszukaj lub dodaj pieska 🐾") },
            trailingIcon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search",
                    modifier = Modifier.clickable {
                        // Search functionality if needed
                    }
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onAddClick,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun DogoListItem(
    dogo: Dogo,
    onClick: () -> Unit,
    onLikeClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Dog avatar
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(dogo.color)
            )

            // Dog info
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = dogo.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = dogo.breed,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Like button
            IconButton(onClick = onLikeClick) {
                Icon(
                    imageVector = if (dogo.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (dogo.isLiked) Color(0xFFE91E63) else LocalContentColor.current
                )
            }

            // Delete button
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete"
                )
            }
        }
    }
}