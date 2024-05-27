package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetApiKeyQueryResult
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GetApiKeyUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun getApiKey(projectName: String, email:String) = try {
        val result = projectRepository.getAPiKey(projectName = projectName, email = email)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        GetApiKeyQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}