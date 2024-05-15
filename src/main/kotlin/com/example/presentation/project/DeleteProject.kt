package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.DeleteProjectQueryResult
import com.example.domain.usecase.project.DeleteProjectUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.deleteProject(){
    authenticate {
        get("${ServerConfig.apiVersion}/deleteProject"){
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val projectName = call.parameters["projectName"]

            if (projectName.isNullOrBlank()) call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                DeleteProjectQueryResult(httpStatusCode = HttpStatusCode.BadRequest.value, message = "Project name parameter can't be null or empty")
            )

            val result = DeleteProjectUseCase().deleteProject(projectName = projectName ?: "")
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result)
        }
    }
}