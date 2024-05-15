package com.example.data.repository

import com.example.common.ResendConfig
import com.example.common.ServerConfig
import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils
import com.example.data.database.Entries
import com.example.data.database.entries.emails.DeliveryStatus
import com.example.data.database.entries.emails.Email
import com.example.data.remote.KtorClient
import com.example.data.remote.dto.SendEmailDto
import com.example.data.remote.dto.bulkEmailDto.BulkEmailResponse
import com.example.data.remote.dto.bulkEmailDto.SendBulkEmailDto
import com.example.data.remote.model.EmailBody
import com.example.domain.repository.EmailRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.resend.Resend
import com.resend.services.emails.model.CreateEmailOptions
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmailRepositoryImpl(
    private val api: KtorClient = KtorClient,
    private val apiConfig: ResendConfig = ResendConfig,
    private val entries:Entries = Entries
) : EmailRepository {

    override suspend fun sendEmailRepository(emailBody: EmailBody): SendEmailDto = withContext(Dispatchers.IO) {

        val resend = Resend(apiConfig.apiKey)
        val params = CreateEmailOptions.builder()
            .from(emailBody.from)
            .to(emailBody.to)
            .subject(emailBody.subject)
            .html(emailBody.text)
            .build()

        return@withContext try {
            val data = resend.emails().send(params)
            SendEmailDto(status = true, id = data.id, message = "Email sent successfully")
        } catch (e: Exception) {
            ServerConfig.logger.warn(e.localizedMessage)
            SendEmailDto(status = false, message = "Failed to send email")
        }

    }

    override suspend fun sendBulkEmailRepository(emailBody: List<EmailBody>): SendBulkEmailDto =
        withContext(Dispatchers.IO) {
            return@withContext try {

                val result = KtorClient.client.post(
                    urlString = "${ResendConfig.resendBasUrl}/emails/batch"
                ) {
                    header(HttpHeaders.Authorization, "Bearer ${ResendConfig.apiKey}")
                    contentType(ContentType.Application.Json)
                    setBody(emailBody)


                }.body<BulkEmailResponse>()
                SendBulkEmailDto(status = true, data = result.data, message = "Bulk emails sent successfully")

            } catch (e: Exception) {
                ServerConfig.logger.warn(e.localizedMessage)
                SendBulkEmailDto(message = "Failed to send Bulk emails")
            }

        }

    override suspend fun createEmailRecord(email: Email): String = withContext(Dispatchers.IO) {
        return@withContext entries.dbEmails.insertOne(email).insertedId?.toString() ?: ""
    }

    override suspend fun updateEmailRecord(emailId:String, deliveryStatus: DeliveryStatus, updateDeliveredTime:Boolean): Boolean = withContext(Dispatchers.IO) {
      entries.dbEmails.findOneAndUpdate(
            Filters.eq(Email::id.name, emailId),
            listOf(
                Updates.set(Email::deliveryStatus.name, deliveryStatus),
                Updates.set(Email::deliveredAt.name, if(updateDeliveredTime) DateAndTimeUtils.currentTime() else null)
            )
        ) ?: return@withContext false
        return@withContext true
    }

}

