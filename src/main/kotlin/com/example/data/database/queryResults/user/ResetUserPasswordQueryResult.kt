package com.example.data.database.queryResults.user

import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
class ResetUserPasswordQueryResult(
    @Contextual
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = ""
)