package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.ResetApiKeyQueryResult
import com.example.domain.usecase.project.ResetApiKeyUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.resetApiKey(){
    authenticate {
        get("${ServerConfig.apiVersion}/project/apiKey/reset"){
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")

            val projectName = call.parameters["projectName"]
            if (projectName == null)  call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                ResetApiKeyQueryResult(message = "Project name parameter can't be null or empty")

            )
            val result = ResetApiKeyUseCase().resetApiKey(projectName = projectName ?: "", email = email)
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result
            )
        }
    }
}