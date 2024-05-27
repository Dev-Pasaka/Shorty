package com.example.data.remote.model.sendEmailBody


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendEmailBody(
    @SerialName("apiKey")
    val apiKey: String,
    @SerialName("emails")
    val emails: List<Email>
)