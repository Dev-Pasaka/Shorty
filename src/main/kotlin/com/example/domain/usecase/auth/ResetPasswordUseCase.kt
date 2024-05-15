package com.example.domain.usecase.auth

import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.ResetUserPasswordQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class ResetPasswordUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {

    suspend fun resetPassword(email: String, newPassword: String, otpCode: String) = try {
        val result = userRepository.resetPassword(
            email = email, newPassword = newPassword, otpCode = otpCode
        )
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        ResetUserPasswordQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }

}