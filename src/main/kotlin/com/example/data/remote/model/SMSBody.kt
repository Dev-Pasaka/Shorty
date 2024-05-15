package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class SMSBody(
    val toRecipient:String,
    val message:String
)