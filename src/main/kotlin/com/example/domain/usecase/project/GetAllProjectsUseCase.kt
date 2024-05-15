package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.DeleteProjectQueryResult
import com.example.data.database.queryResults.project.GetAllProjectQueryResults
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GetAllProjectsUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun getAllProjects(email:String) = try {
        val result = projectRepository.getAllProject(email = email)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.info(Json.encodeToString(e.localizedMessage))
        GetAllProjectQueryResults(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}