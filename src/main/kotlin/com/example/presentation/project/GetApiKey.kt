package com.example.presentation.project

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetApiKeyQueryResult
import com.example.domain.usecase.project.GetApiKeyUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.getApiKey(){
    authenticate {
        get("${ServerConfig.apiVersion}/getApiKey"){

            val projectName = call.parameters["projectName"]
            if (projectName == null){
                call.respond(
                    status =HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request")  ,
                    GetApiKeyQueryResult(message = "Project name parameter can't be null or empty")
                )
            }
            val result = GetApiKeyUseCase().getApiKey(projectName = projectName ?: "")
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result
            )
        }
    }
}