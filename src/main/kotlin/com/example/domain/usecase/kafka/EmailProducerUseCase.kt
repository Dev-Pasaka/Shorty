package com.example.domain.usecase.kafka

import com.example.common.KafkaConfig
import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.database.entries.emails.DeliveryStatus
import com.example.data.database.entries.emails.Email
import com.example.data.dto.kafka.KafkaProducerResponseDto
import com.example.data.repository.EmailRepositoryImpl
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.repository.EmailRepository
import com.example.plugins.kafka.procuders.configureKafkaProducer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class EmailProducerUseCase(
    private val emailRepository: EmailRepository = EmailRepositoryImpl()
) {
    suspend fun produceEmail(emailMessage: EmailMessage): KafkaProducerResponseDto = withContext(Dispatchers.IO) {

        emailMessage.emailBody.forEach {email ->
            emailRepository.createEmailRecord(
                email = Email(
                    id = email.id,
                    from = email.from,
                    to = email.to,
                    subject = email.subject,
                    message = email.text,
                    deliveryStatus = DeliveryStatus.PENDING,
                    createdAt = DateAndTimeUtils.currentTime(),
                )
            )
        }

        val json = Json.encodeToString(
            emailMessage.copy(
                emailBody = emailMessage.emailBody.map { it.copy(id = it.id) }
            )
        )

        configureKafkaProducer(topic = KafkaConfig.EMAIL_TOPIC, message = json)


    }
}




