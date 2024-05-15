package com.example.data.database.queryResults.user

import com.example.domain.model.user.UserData
import io.ktor.http.*
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class GetUserQueryResult(
    @Contextual
    val httpStatusCode: Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = "",
    val userData: UserData? = null
)