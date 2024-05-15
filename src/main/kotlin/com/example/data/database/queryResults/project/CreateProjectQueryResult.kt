package com.example.data.database.queryResults.project

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class CreateProjectQueryResult(
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val apiKey:String = "",
    val message:String = ""
)