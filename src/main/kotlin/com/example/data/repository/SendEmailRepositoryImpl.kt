package com.example.data.repository

import com.example.common.PaviconTechCommConfig
import com.example.data.remote.KtorClient
import com.example.data.remote.dto.emailDto.EmailDto
import com.example.data.remote.model.sendEmailBody.Email
import com.example.data.remote.model.sendEmailBody.SendEmailBody
import com.example.domain.repository.SendEmailsRepository
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SendEmailRepositoryImpl(
    private val api: KtorClient = KtorClient,
    private val paviconTechCommConfig: PaviconTechCommConfig = PaviconTechCommConfig,
) : SendEmailsRepository {
    override suspend fun sendOnboardingOtp(recipientEmail: String, recipientName: String, otpCode: String): EmailDto =
        withContext(Dispatchers.IO) {
            val emailBody = onBoardingOtpEmailTemplate(recipientName, otpCode)
            api.client.post("${paviconTechCommConfig.baseUrl}${paviconTechCommConfig.emailEndpoint}") {
                contentType(ContentType.Application.Json)
                setBody(
                    SendEmailBody(
                        apiKey = paviconTechCommConfig.apiKey,
                        emails = listOf(
                            Email(
                                from = paviconTechCommConfig.fromEmail,
                                html = emailBody,
                                subject = "Account Verification",
                                to = recipientEmail
                            )
                        )
                    )
                )
            }.body<EmailDto>()
        }

    override suspend fun sendOtp(recipientEmail:String,recipientName:String, otpCode:String, subject:String): EmailDto = withContext(Dispatchers.IO) {
        val emailBody = email2FATemplate(recipientName, otpCode)
        api.client.post("${paviconTechCommConfig.baseUrl}${paviconTechCommConfig.emailEndpoint}") {
            contentType(ContentType.Application.Json)
            setBody(
                SendEmailBody(
                    apiKey = paviconTechCommConfig.apiKey,
                    emails = listOf(
                        Email(
                            from = paviconTechCommConfig.fromEmail,
                            html = emailBody,
                            subject = subject,
                            to = recipientEmail
                        )
                    )
                )
            )
        }.body<EmailDto>()

    }
}


