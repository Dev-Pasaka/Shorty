package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.dto.shorten.QuickiesDto
import com.example.data.dto.shorten.ShortenDto
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import com.example.domain.usecase.shortenUrl.CreateShortUrlUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.quickies() {
    post("${ServerConfig.apiVersion}/quickies") {
        val data = call.receive<QuickiesDto>()


        /** Create quickies url */
        val result = CreateShortUrlUseCase().createShortUrl(
            shortenUrl = ShortenUrl(projectName = data.projectName, longUrl = data.longUrl)
        )
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )
    }

}
