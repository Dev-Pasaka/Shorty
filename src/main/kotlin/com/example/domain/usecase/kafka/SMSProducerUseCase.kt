package com.example.domain.usecase.kafka

import com.example.common.KafkaConfig
import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.database.entries.emails.DeliveryStatus
import com.example.data.database.entries.emails.Email
import com.example.data.dto.kafka.KafkaProducerResponseDto
import com.example.data.remote.model.SMSBody
import com.example.data.repository.SMSRepositoryImpl
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.repository.SMSRepository
import com.example.plugins.kafka.procuders.configureKafkaProducer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SMSProducerUseCase(
) {
    suspend fun produceSMS(smsBody: List<SMSBody>): KafkaProducerResponseDto = withContext(Dispatchers.IO) {

        val json = Json.encodeToString(smsBody)
        configureKafkaProducer(topic = KafkaConfig.SMS_TOPIC, message = json)

    }
}