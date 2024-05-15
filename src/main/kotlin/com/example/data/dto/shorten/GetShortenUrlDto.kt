package com.example.data.dto.shorten

import kotlinx.serialization.Serializable

@Serializable
data class GetShortenUrlDto(
    val projectName:String,
    val longUrl:String?,
    val shortUrl: String?,
    val clicks:Int,
    val createdAt:String
)