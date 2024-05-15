package com.example.presentation.email

import com.example.common.ServerConfig
import com.example.data.remote.model.EmailBody
import com.example.data.remote.model.SendEmailData
import com.example.data.responses.UnauthorizedResponse
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.usecase.kafka.EmailProducerUseCase
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendEmails() {
    post("${ServerConfig.apiVersion}/sendEmail") {

        /** Check if api key header is blank*/
        val emailBody = call.receive<SendEmailData>()




        val isApiKeyValid = VerifyApiKeyUseCase().verifyApiKey(apiKey = emailBody.apiKey)

        if (!isApiKeyValid.status) call.respond(
            status = HttpStatusCode(value = isApiKeyValid.httpStatusCode, description = isApiKeyValid.message),
            message = isApiKeyValid
        )

        /** Produce email to kafka topic*/
        val sendEmailResult = EmailProducerUseCase().produceEmail(
            emailMessage = EmailMessage(
                emailBody = emailBody.emails
            )
        )
        call.respond(
            status = HttpStatusCode(value = sendEmailResult.httpStatus, description = sendEmailResult.message),
            message = sendEmailResult
        )
    }


}