package com.example.data.database.queryResults.shortenUrl

import com.example.data.dto.shorten.GetShortenUrlDto
import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class GetAllShortenedUrlQueryResult(
    @Contextual
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
    val shortenUrls:List<GetShortenUrlDto> = emptyList()
)