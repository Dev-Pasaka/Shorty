package com.example.domain.usecase.user

import com.example.common.ServerConfig
import com.example.data.database.queryResults.user.UpdateUserQueryResult
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.model.user.UpdateUser
import com.example.domain.repository.UserRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class UpdateUserInfoUseCase(
    private val userRepository: UserRepository = UserRepositoryImpl()
) {
    suspend fun updateUserInfo(email:String, userInfo: UpdateUser) = try {
        val result = userRepository.updateUser(email = email, user = userInfo)
        ServerConfig.logger.info(Json.encodeToString(result))
        result

    }catch (e:Exception){
        ServerConfig.logger.info(Json.encodeToString(e.localizedMessage))
        UpdateUserQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}