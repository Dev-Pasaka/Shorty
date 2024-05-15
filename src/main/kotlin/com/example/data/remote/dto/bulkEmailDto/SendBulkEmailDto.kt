package com.example.data.remote.dto.bulkEmailDto


import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendBulkEmailDto(
    val httpStatus:Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    @SerialName("data")
    val data: List<DataX> = emptyList(),
    val message:String = ""
)