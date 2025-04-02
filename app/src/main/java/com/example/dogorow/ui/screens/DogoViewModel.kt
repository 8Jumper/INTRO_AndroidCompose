package com.example.dogorow.ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dogorow.data.Dogo

class DogoViewModel : ViewModel() {
    // List of all dogs
    private val _dogos = mutableStateListOf<Dogo>()
    val dogos: List<Dogo> get() = _dogos

    // Search query
    private val _searchQuery = mutableStateOf("")
    val searchQuery get() = _searchQuery.value

    // Filtered dogs based on search
    val filteredDogos get() = if (searchQuery.isEmpty()) {
        dogos
    } else {
        dogos.filter {
            it.name.contains(searchQuery, ignoreCase = true) ||
                    it.breed.contains(searchQuery, ignoreCase = true)
        }
    }

    // Total liked count
    val likedCount get() = dogos.count { it.isLiked }

    init {
        // Initialize with some sample dogs
        for (i in 1..3) {
            _dogos.add(
                Dogo(
                    id = i,
                    name = "Pan Pumpernikiel",
                    breed = "Jack Russel"
                )
            )
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleLike(dogoId: Int) {
        val index = _dogos.indexOfFirst { it.id == dogoId }
        if (index != -1) {
            _dogos[index] = _dogos[index].copy(isLiked = !_dogos[index].isLiked)
        }
    }

    fun deleteDogo(dogoId: Int) {
        _dogos.removeIf { it.id == dogoId }
    }

    fun getDogo(dogoId: Int): Dogo? {
        return _dogos.find { it.id == dogoId }
    }

    fun addDogo(name: String, breed: String) {
        val newId = if (_dogos.isEmpty()) 1 else _dogos.maxOf { it.id } + 1
        _dogos.add(Dogo(id = newId, name = name, breed = breed))
    }
}