package com.example.presentation.user

import com.example.common.ServerConfig
import com.example.domain.usecase.user.GetUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getUserData() {
    authenticate {
        get("${ServerConfig.apiVersion}/getUserData") {
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val userResult = GetUserUseCase().getUserData(email = email)
            call.respond(status =  HttpStatusCode(value = userResult.httpStatusCode, description = userResult.message), userResult)
        }
    }
}