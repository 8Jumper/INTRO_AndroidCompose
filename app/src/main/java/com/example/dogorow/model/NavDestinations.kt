package com.example.dogorow.model

import kotlinx.serialization.Serializable

@Serializable
object dogos

@Serializable
data class DogoListScreen(val name: String)
