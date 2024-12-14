package com.example.digidex.repositories
import com.example.digidex.models.Digimon
import com.example.digidex.network.WebService

class DigimonRepository(private val webService: WebService) {

    suspend fun fetchAllDigimon(): List<Digimon> {
        return webService.getAllDigimon()
    }

    suspend fun searchDigimonByName(name: String): List<Digimon> {
        return webService.getDigimonByName(name)
    }

    suspend fun fetchDigimonByLevel(level: String): List<Digimon> {
        return webService.getDigimonByLevel(level)
    }
}

