package com.example.dogorow

import androidx.compose.ui.graphics.Color

data class Dogo(
    val id: Int,
    val name: String,
    val breed: String,
    val color: Color = Color(0xFFB76EDB), // Purple color from mockup
    var isLiked: Boolean = false
)