package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.domain.usecase.shortenUrl.GetAllShortenedUrlsUseCase
import com.example.domain.usecase.shortenUrl.GetLongUrlUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getLongUrl() {
    get("${ServerConfig.apiVersion}/getLongUrl") {
        val shortUrl = call.parameters["shortUrl"]
        if (shortUrl.isNullOrBlank()) {
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                GetProjectQueryResults(message = "Short url parameter can't be null or empty")
            )
        }

        /** Get Long url */
        val result = GetLongUrlUseCase().getLongUrl(shortUrl = shortUrl ?: "")
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )
    }
}