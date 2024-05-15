package com.example.domain.usecase.consumer

import com.example.common.KafkaConfig
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.usecase.emails.SendEmailsUseCase
import com.example.domain.usecase.kafka.EmailConsumerUseCase

import kotlinx.serialization.json.Json

class ConsumeEmailsUseCase(
    private val sendEmailsUseCase: SendEmailsUseCase = SendEmailsUseCase()
) {
    suspend fun consumeEmails(){
        EmailConsumerUseCase().configureKafkaConsumer(topic = KafkaConfig.EMAIL_TOPIC).collect{ message ->
            val emailMessage = Json.decodeFromString<EmailMessage>(message)
            sendEmailsUseCase.sendEmails(emailMessage = emailMessage)
        }
    }
}