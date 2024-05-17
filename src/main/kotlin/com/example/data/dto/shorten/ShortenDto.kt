package com.example.data.dto.shorten

import kotlinx.serialization.Serializable

@Serializable
data class ShortenDto(
    val apiKey:String,
    val projectName:String,
    val longUrl:String,
    val email:String
)
