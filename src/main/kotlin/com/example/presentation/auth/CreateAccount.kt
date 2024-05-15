package com.example.presentation.auth

import com.example.common.ServerConfig
import com.example.data.database.entries.user.User
import com.example.data.dto.user.CreateUser
import com.example.domain.usecase.auth.RegisterUserUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.createAccount(){
    post("${ServerConfig.apiVersion}/register"){
        val userData = call.receive<CreateUser>()
        val registerUser = RegisterUserUseCase().register(
            user = User(
                firstName = userData.firstName,
                lastName = userData.lastName,
                email = userData.email,
                phone = userData.phone,
                password = userData.password
            )
        )
        call.respond(status = HttpStatusCode(value = registerUser.httpStatusCode, description = registerUser.message), registerUser)
    }
}