package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.data.dto.user.ResetPasswordCredentials
import com.example.domain.usecase.auth.ResetPasswordUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.resetPassword() {
    post("${ServerConfig.apiVersion}/resetPassword"){
        val credentials = call.receive<ResetPasswordCredentials>()
        val resetPasswordResult = ResetPasswordUseCase().resetPassword(
            email = credentials.email, newPassword = credentials.newPassword , otpCode = credentials.otpCode
        )
        call.respond(status =  HttpStatusCode(value = resetPasswordResult.httpStatusCode, description = resetPasswordResult.message), resetPasswordResult)
    }
}