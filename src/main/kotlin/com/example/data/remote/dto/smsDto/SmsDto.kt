package com.example.data.remote.dto.smsDto

import com.example.data.remote.dto.bulkEmailDto.DataX
import io.ktor.http.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SmsDto(
    val httpStatus:Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    @SerialName("data")
    val data: List<SmsResponseDtoItem> = emptyList(),
    val message:String = ""
)

