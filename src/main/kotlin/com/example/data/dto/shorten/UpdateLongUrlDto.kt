package com.example.data.dto.shorten

import kotlinx.serialization.Serializable
import org.apache.kafka.common.protocol.types.Field.Str
@Serializable
data class UpdateLongUrlDto(
    val apiKey:String,
    val shortUrl:String,
    val longUrl:String
)