package com.example.presentation.sms

import com.example.common.ServerConfig
import com.example.data.remote.model.EmailBody
import com.example.data.remote.model.SMSBody
import com.example.data.remote.model.SendSMSData
import com.example.data.responses.UnauthorizedResponse
import com.example.domain.model.kafka.EmailMessage
import com.example.domain.usecase.kafka.EmailProducerUseCase
import com.example.domain.usecase.kafka.SMSProducerUseCase
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.sendSms(){
    post("${ServerConfig.apiVersion}/sendSMS") {

        /** Check if api key header is blank*/
        val smsBody = call.receive<SendSMSData>()



        /** Check if api key is valid */
        val isApiKeyValid = VerifyApiKeyUseCase().verifyApiKey(apiKey = smsBody.apiKey)

        if (!isApiKeyValid.status) call.respond(
            status = HttpStatusCode(value = isApiKeyValid.httpStatusCode, description = isApiKeyValid.message),
            message = isApiKeyValid
        )

        /** Produce sms to kafka topic*/
        val sendEmailResult = SMSProducerUseCase().produceSMS(
            smsBody = smsBody.sms
        )
        call.respond(
            status = HttpStatusCode(value = sendEmailResult.httpStatus, description = sendEmailResult.message),
            message = sendEmailResult
        )
    }

}

