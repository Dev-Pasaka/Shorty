package com.example.domain.usecase.project

import com.example.common.ServerConfig
import com.example.data.database.entries.project.Project
import com.example.data.database.queryResults.project.CreateProjectQueryResult
import com.example.data.dto.project.CreateProject
import com.example.data.dto.project.ProjectType
import com.example.data.dto.project.SubscriptionType
import com.example.data.repository.ProjectRepositoryImpl
import com.example.domain.repository.ProjectRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class CreateProjectUseCase(
    private val projectRepository: ProjectRepository = ProjectRepositoryImpl()
) {
    suspend fun createProject(email:String, project: CreateProject) = try{

        val result = projectRepository.createProject(
            project = Project(
                projectName = project.projectName,
                email = email,
                projectType = project.projectType,
                subscriptionType = project.subscriptionType
            )
        )
        ServerConfig.logger.info(Json.encodeToString(result))
        result

    }catch (e:Exception){
        ServerConfig.logger.info(Json.encodeToString(e.localizedMessage))
        CreateProjectQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }

}

