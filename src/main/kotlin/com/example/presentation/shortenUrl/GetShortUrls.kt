package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import com.example.domain.usecase.shortenUrl.CreateShortUrlUseCase
import com.example.domain.usecase.shortenUrl.GetAllShortenedUrlsUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getShortUrls() {
    get("${ServerConfig.apiVersion}/getShortUrls/{projectName?}") {
        val projectName = call.parameters["projectName"]
        if (projectName.isNullOrBlank()) {
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                GetProjectQueryResults(message = "Project name parameter can't be null or empty")
            )
        }

        /** Get all shortened urls */
        val result = GetAllShortenedUrlsUseCase().getUrls(projectName = projectName ?: "")
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )
    }


}