package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.data.database.queryResults.shortenUrl.SendOtpQueryResult
import com.example.domain.usecase.auth.SendPasswordResetOtpUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.passwordResetOtp() {
    get("${ServerConfig.apiVersion}/user/reset/otp/{email?}"){
        val email = call.parameters["email"]
        if (email.isNullOrBlank()) {
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                SendOtpQueryResult(message = "Email parameter can't be null or empty")
            )
        }

        val result = SendPasswordResetOtpUseCase().sendOtp(email = email ?: "")
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )

    }
}