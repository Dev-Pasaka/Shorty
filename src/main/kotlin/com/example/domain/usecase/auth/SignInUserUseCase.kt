package com.example.domain.usecase.auth

import ch.qos.logback.classic.Logger
import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.SignInUserQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SignInUserUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun signIn(email:String,  password:String) = try {
        val result = userRepository.signIn(email = email, password = password)
        ServerConfig.logger.info(Json.encodeToString<SignInUserQueryResult>(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(e.localizedMessage)
        SignInUserQueryResult(httpStatusCode = HttpStatusCode.ExpectationFailed.value, message = "An expected error occurred")
    }
}