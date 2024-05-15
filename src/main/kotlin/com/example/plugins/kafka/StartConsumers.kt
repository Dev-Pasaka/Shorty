package com.example.plugins.kafka

import com.example.domain.usecase.consumer.ConsumeEmailsUseCase
import com.example.domain.usecase.kafka.SMSConsumerUseCase
import io.ktor.server.application.Application
import kotlinx.coroutines.*

fun Application.startConsumers() {
    val emailScope = CoroutineScope(Dispatchers.IO)
    val smsScope = CoroutineScope(Dispatchers.IO)

    emailScope.launch {
        ConsumeEmailsUseCase().consumeEmails()
    }
    smsScope.launch {
        SMSConsumerUseCase().consumeSMS()
    }
}