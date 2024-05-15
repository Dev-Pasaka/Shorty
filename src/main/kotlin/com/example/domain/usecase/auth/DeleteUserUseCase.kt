package com.example.domain.usecase.auth

import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.DeleteUserQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DeleteUserUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun deleteUser(email:String) = try {
        val result = userRepository.deleteUser(email = email)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        DeleteUserQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}