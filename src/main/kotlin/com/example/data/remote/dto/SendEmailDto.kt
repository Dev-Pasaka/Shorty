package com.example.data.remote.dto


import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendEmailDto(
    val httpStatus:Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    @SerialName("id")
    val id: String? = null,
    val message:String = ""
)