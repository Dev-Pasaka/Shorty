package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.VerifyApiKeyQueryResult
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class VerifyApiKeyUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun verifyApiKey(apiKey:String) = try {
        val result = projectRepository.verifyApiKey(apiKey = apiKey)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        VerifyApiKeyQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected server error has occured")
    }
}