package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.data.database.queryResults.project.ResetApiKeyQueryResult
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GetProjectUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun getProject(projectName: String, email:String) = try {
        val result = projectRepository.getProject(projectName = projectName, email = email)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        GetProjectQueryResults(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}