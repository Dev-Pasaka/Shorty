package com.example.domain.usecase.emails

import com.example.data.database.entries.emails.DeliveryStatus
import com.example.data.repository.EmailRepositoryImpl
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.model.kafka.EmailType
import com.example.domain.repository.EmailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SendEmailsUseCase(
    private val repository: EmailRepository = EmailRepositoryImpl()
) {
    suspend fun sendEmails(emailMessage: EmailMessage) = withContext(Dispatchers.IO){
        when(emailMessage.emailType){
            EmailType.SINGLE_EMAIL ->{
                val result = repository.sendEmailRepository(emailBody = emailMessage.emailBody.first())
                when(result.status){
                    true -> emailMessage.emailBody.forEach {
                        launch {
                            repository.updateEmailRecord(it.id, deliveryStatus = DeliveryStatus.SUCCESSFUL, updateDeliveredTime = true)
                        }
                    }
                    false ->{
                        emailMessage.emailBody.forEach {
                            launch {
                                repository.updateEmailRecord(it.id, deliveryStatus = DeliveryStatus.FAILED)
                            }
                        }
                    }
                }
            }
            EmailType.BULK_EMAILS ->{
                val result = repository.sendBulkEmailRepository(emailBody = emailMessage.emailBody)
                when(result.status){
                    true -> emailMessage.emailBody.forEach {
                        launch {
                            repository.updateEmailRecord(it.id, deliveryStatus = DeliveryStatus.SUCCESSFUL, updateDeliveredTime = true)
                        }
                    }
                    false ->{
                        emailMessage.emailBody.forEach {
                            launch {
                                repository.updateEmailRecord(it.id, deliveryStatus = DeliveryStatus.FAILED)
                            }
                        }
                    }
                }
            }
        }

    }
}