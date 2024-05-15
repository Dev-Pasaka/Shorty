package com.example.domain.usecase.kafka

import com.example.common.KafkaConfig
import com.example.data.remote.model.SMSBody
import com.example.data.repository.SMSRepositoryImpl
import com.example.domain.repository.SMSRepository
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SMSConsumerUseCase(
    private val smsRepository: SMSRepository = SMSRepositoryImpl()
) {
    suspend fun consumeSMS(){
        EmailConsumerUseCase().configureKafkaConsumer(topic = KafkaConfig.SMS_TOPIC).collect{ message ->
            val smsBody = Json.decodeFromString<List<SMSBody>>(message)
            smsRepository.sendSMS(smsBody = smsBody)
        }
    }
}