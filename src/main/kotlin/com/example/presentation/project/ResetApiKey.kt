package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.ResetApiKeyQueryResult
import com.example.domain.usecase.project.ResetApiKeyUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.resetApiKey(){
    authenticate {
        get("${ServerConfig.apiVersion}/resetApiKey"){
            val projectName = call.parameters["projectName"]

            if (projectName.isNullOrBlank())  call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                ResetApiKeyQueryResult(message = "Project name parameter can't be null or empty")

            )
            val result = ResetApiKeyUseCase().resetApiKey(projectName = projectName ?: "")
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                result
            )
        }
    }
}