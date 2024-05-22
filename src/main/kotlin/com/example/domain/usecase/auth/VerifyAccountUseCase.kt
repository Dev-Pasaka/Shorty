package com.example.domain.usecase.auth

import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.VerifyAccountQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*

class VerifyAccountUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun verifyAccount(email:String, otpCode:String) = try {
        val result = userRepository.verifyUserAccount(email, otpCode)
        ServerConfig.logger.info("Account Verification : $result")
        result
    }catch (e:Exception){
        ServerConfig.logger.info("Account Verification : ${e.localizedMessage}")
        VerifyAccountQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}