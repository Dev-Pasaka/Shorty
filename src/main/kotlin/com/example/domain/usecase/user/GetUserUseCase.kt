package com.example.domain.usecase.user

import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.GetUserQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class GetUserUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun getUserData(email:String) =try{
        val result = userRepository.getUser(email = email)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.info(Json.encodeToString(e.localizedMessage))
        GetUserQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}