package com.example.presentation.user

import com.example.common.ServerConfig
import com.example.domain.model.user.UpdateUser
import com.example.domain.usecase.user.UpdateUserInfoUseCase
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateUserInfo() {
    authenticate {
        post("${ServerConfig.apiVersion}/updateUserInfo") {
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val userInfo = call.receive<UpdateUser>()
            val updateUserResult = UpdateUserInfoUseCase().updateUserInfo(email = email, userInfo = userInfo)
            call.respond(status =  HttpStatusCode(value = updateUserResult.httpStatusCode, description = updateUserResult.message), updateUserResult)
        }
    }
}

