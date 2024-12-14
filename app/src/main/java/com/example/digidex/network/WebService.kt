package com.example.digidex.network

import com.example.digidex.models.Digimon
import retrofit2.http.GET
import retrofit2.http.Path

interface WebService {
    @GET("/api/digimon")
    suspend fun getAllDigimon(): List<Digimon>

    @GET("/api/digimon/name/{name}")
    suspend fun getDigimonByName(
        @Path("name") name: String
    ): List<Digimon>

    @GET("/api/digimon/level/{level}")
    suspend fun getDigimonByLevel(
        @Path("level") level: String
    ): List<Digimon>
}