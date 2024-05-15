package com.example.data.dto.kafka

import io.ktor.http.*
import kotlinx.serialization.Serializable

@Serializable
data class KafkaProducerResponseDto(
    val httpStatus:Int = HttpStatusCode.OK.value,
    val status:Boolean = false,
    val message:String = ""
)
