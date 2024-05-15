package com.example.data.database.queryResults.shortenUrl

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class UpdateLongQueryResult(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
)