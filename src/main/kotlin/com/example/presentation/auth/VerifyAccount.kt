package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.domain.usecase.auth.VerifyAccountUseCase
import com.example.presentation.auth.requestBody.VerifyAccount
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.verifyAccount() {
    post("${ServerConfig.apiVersion}/user/verify") {
        val verifyAccountCredentials = call.receive<VerifyAccount>()
        val result = VerifyAccountUseCase().verifyAccount(email = verifyAccountCredentials.email, otpCode = verifyAccountCredentials.otpCode)
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )
    }
}