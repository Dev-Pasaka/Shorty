package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.common.utils.dateAndTimeUtils.Utils
import com.example.data.dto.shorten.DeleteShortUrlDto
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import com.example.domain.usecase.shortenUrl.DeleteShortenedUrlUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteShortUrl() {
    delete("${ServerConfig.apiVersion}/shorty/delete"){
        val data = call.receive<DeleteShortUrlDto>()
        val isApiKeyValid = VerifyApiKeyUseCase().verifyApiKey(apiKey = data.apiKey)

        when(isApiKeyValid.status){
            false ->{
                call.respond(
                    status = HttpStatusCode(value = isApiKeyValid.httpStatusCode, description = isApiKeyValid.message),
                    message = isApiKeyValid
                )
            }
            true ->{
                /** Delete shortened url */
                val result = DeleteShortenedUrlUseCase().deleteShortenedUrl(
                    shortUrl = Utils.extractUrlComponents(data.shortUrl).path
                )
                call.respond(
                    status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
                    message = result
                )
            }
        }
    }
}