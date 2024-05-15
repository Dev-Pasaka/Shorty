package com.example.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
class SendEmailData(
    val apiKey:String,
    val emails:List<EmailBody>
)