package com.example.presentation.auth.requestBody

import kotlinx.serialization.Serializable
import org.apache.kafka.common.protocol.types.Field.Str

@Serializable
data class VerifyAccount(
    val email:String,
    val otpCode:String
)