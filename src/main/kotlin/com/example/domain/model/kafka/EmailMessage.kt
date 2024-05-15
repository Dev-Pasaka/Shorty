package com.example.domain.model.kafka

import com.example.data.remote.model.EmailBody
import kotlinx.serialization.Serializable

@Serializable
data class EmailMessage(
    val emailType:EmailType = EmailType.SINGLE_EMAIL,
    val emailBody:List<EmailBody> = emptyList()
)
