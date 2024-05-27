package com.example.data.remote.model.sendEmailBody


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Email(
    @SerialName("from")
    val from: String,
    @SerialName("html")
    val html: String,
    @SerialName("subject")
    val subject: String,
    @SerialName("to")
    val to: String
)