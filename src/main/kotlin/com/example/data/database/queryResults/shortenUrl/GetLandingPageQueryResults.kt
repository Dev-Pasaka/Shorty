package com.example.data.database.queryResults.shortenUrl

import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
@Serializable
data class GetLandingPageQueryResults(
    @Contextual
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
    val data:LandingPageData? = null
)
@Serializable
data class LandingPageData(
    val shortenedUrls:Int,
    val clicks:Int,
    val activeUsers:Int,
)
