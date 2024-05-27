package com.example.data.repository

import com.example.common.IP2Config
import com.example.data.remote.KtorClient
import com.example.data.remote.dto.locationDto.LocationDto
import com.example.domain.repository.LocationRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val api:KtorClient = KtorClient,
    private val ip2Config:IP2Config = IP2Config
):LocationRepository {
    override suspend fun getLocation(ipAddress: String): LocationDto  = withContext(Dispatchers.IO){
        api.client.get("${ip2Config.baseUrl}${ip2Config.apiKey}"){
            parameter(key = "ip", ipAddress)
        }.body<LocationDto>()
    }
}

/*suspend fun main(){
    println(
        KtorClient.client.get("${IP2Config.baseUrl}${IP2Config.apiKey}"){
            parameter(key = "ip", "localhost")
        }.bodyAsText()
    )
}*/

