package com.example.data.remote.dto.emailDto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EmailDto(
    @SerialName("httpStatus")
    val httpStatus: Int,
    @SerialName("message")
    val message: String,
    @SerialName("status")
    val status: Boolean
)