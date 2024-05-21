package com.example.data.dto.shorten

import com.example.data.database.entries.shortenUrl.Analytics
import kotlinx.serialization.Serializable

@Serializable
data class GetShortenUrlDto(
    val projectName:String,
    val longUrl:String?,
    val shortUrl: String?,
    val clicks:Int,
    val analytics: List<Analytics> = emptyList(),
    val createdAt:String
)