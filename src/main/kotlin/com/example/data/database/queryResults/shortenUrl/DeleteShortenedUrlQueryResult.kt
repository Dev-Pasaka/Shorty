package com.example.data.database.queryResults.shortenUrl

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class DeleteShortenedUrlQueryResult(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
)
