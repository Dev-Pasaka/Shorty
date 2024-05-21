package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.domain.usecase.auth.DeleteUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteUser(){
    authenticate {
        delete("${ServerConfig.apiVersion}/auth/delete") {
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val deleteUserResult = DeleteUserUseCase().deleteUser(email = email)
            call.respond(status =  HttpStatusCode(value = deleteUserResult.httpStatusCode, description = deleteUserResult.message),deleteUserResult)
        }
    }
}