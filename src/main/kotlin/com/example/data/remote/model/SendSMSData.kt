package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SendSMSData(
    val apiKey:String,
    val sms:List<SMSBody>
)
