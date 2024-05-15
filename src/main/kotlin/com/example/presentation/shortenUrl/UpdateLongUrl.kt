package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.dto.shorten.ShortenDto
import com.example.data.dto.shorten.UpdateLongUrlDto
import com.example.domain.usecase.project.VerifyApiKeyUseCase
import com.example.domain.usecase.shortenUrl.CreateShortUrlUseCase
import com.example.domain.usecase.shortenUrl.UpdateLongUrlUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.updateLongUrl() {
    post("${ServerConfig.apiVersion}/updateLongUrl"){
        val data = call.receive<UpdateLongUrlDto>()

        val isApiKeyValid = VerifyApiKeyUseCase().verifyApiKey(apiKey = data.apiKey)

        when(isApiKeyValid.status){
            false ->{
                call.respond(
                    status = HttpStatusCode(value = isApiKeyValid.httpStatusCode, description = isApiKeyValid.message),
                    message = isApiKeyValid
                )
            }
            true ->{

                /** Update long url */
                val result = UpdateLongUrlUseCase().updateLongUrl(
                    shortUrl = data.shortUrl,
                    longUrl = data.longUrl
                )
                call.respond(
                    status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
                    message = result
                )
            }
        }
    }
}