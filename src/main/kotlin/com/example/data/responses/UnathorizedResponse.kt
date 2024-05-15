package com.example.data.responses

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class UnauthorizedResponse(
    val httpStatusCode: Int = HttpStatusCode.Unauthorized.value,
    val message: String = "Invalid or expired token"
)