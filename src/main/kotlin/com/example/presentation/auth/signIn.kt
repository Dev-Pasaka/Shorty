package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.data.dto.user.UserCredentials
import com.example.domain.usecase.auth.SignInUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun Route.signIn(){
    post("${ServerConfig.apiVersion}/auth/signIn"){
        val credentials = call.receive<UserCredentials>()
        val signInResult = SignInUserUseCase().signIn(email = credentials.email, password = credentials.password)
        call.respond(
            status =  HttpStatusCode(value = signInResult.httpStatusCode, description = signInResult.message),
            message = signInResult
        )
    }
}