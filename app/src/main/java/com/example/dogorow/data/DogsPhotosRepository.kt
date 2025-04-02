package com.example.dogorow.data

import com.example.dogorow.data.network.DogsService
import com.example.dogorow.model.DogPhoto

interface DogsPhotosRepository {
    suspend fun getRandomDogImage(): DogPhoto
}

class NetworkDogsPhotosRepository(
    private val dogsService: DogsService,
) : DogsPhotosRepository {

    override suspend fun getRandomDogImage(): DogPhoto = dogsService.getRandomDogImage()
}