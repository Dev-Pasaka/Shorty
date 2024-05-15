package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.DeleteProjectQueryResult
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DeleteProjectUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun deleteProject(projectName: String) = try {
        val result = projectRepository.deleteProject(projectName = projectName)
        ServerConfig.logger.info(Json.encodeToString(result))
        result

    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        DeleteProjectQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}