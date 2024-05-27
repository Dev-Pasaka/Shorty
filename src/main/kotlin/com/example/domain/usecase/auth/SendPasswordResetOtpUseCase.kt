package com.example.domain.usecase.auth

import com.example.common.ServerConfig
import com.example.data.database.queryResults.shortenUrl.SendOtpQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*

class SendPasswordResetOtpUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun sendOtp(email:String) = try {
        val result = userRepository.sendOtp(email)
        ServerConfig.logger.info("Send Password Reset Otp: $result")
        result
    }catch (e:Exception){
        ServerConfig.logger.warn("Send Password Reset Otp: ${e.localizedMessage}")
        SendOtpQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}