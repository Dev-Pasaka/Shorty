package com.example.data.remote

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object KtorClient {

    val client = HttpClient(CIO){
        install(ContentNegotiation){
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true // Ignore unknown keys in JSON
            })
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
            engine {
                // Set timeout
                requestTimeout = 10000 // 5000 milliseconds = 5 seconds

            }
        }
    }
}