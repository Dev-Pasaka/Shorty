package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.DeleteProjectQueryResult
import com.example.domain.usecase.project.DeleteProjectUseCase
import com.example.domain.usecase.project.GetAllProjectsUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getAllProjects(){
    authenticate {
        get("${ServerConfig.apiVersion}/project/all"){
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val result = GetAllProjectsUseCase().getAllProjects(email)
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result
            )
        }
    }
}