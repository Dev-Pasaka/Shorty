package com.example.data.dto.shorten

import kotlinx.serialization.Serializable


@Serializable
data class DeleteShortUrlDto(
    val apiKey:String,
    val shortUrl:String,
)