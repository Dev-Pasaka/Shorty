package com.example.domain.usecase.auth

import com.example.common.ServerConfig
import com.example.data.database.entries.user.User
import com.example.data.database.queryResults.user.CreateUserQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RegisterUserUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun register(user: User) = try{
        val result = userRepository.createUser(user = user)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        CreateUserQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}