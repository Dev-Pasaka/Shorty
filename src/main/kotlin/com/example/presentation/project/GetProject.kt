package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.domain.usecase.project.GetProjectUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getProject(){
    authenticate {
        get("${ServerConfig.apiVersion}/project"){

            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")

            val projectName = call.parameters["projectName"]
            if (projectName.isNullOrBlank()){
                call.respond(
                    status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request")  ,
                    GetProjectQueryResults(message = "Project name parameter can't be null or empty")
                )
            }
            val result = GetProjectUseCase().getProject(projectName = projectName ?: "", email = email)
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result
            )
        }
    }
}