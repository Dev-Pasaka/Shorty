package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.domain.usecase.project.GetAllProjectsUseCase
import com.example.domain.usecase.shortenUrl.GetAllShortenUrlByEmailUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllShortUrls() {
    authenticate {
        get("${ServerConfig.apiVersion}/shorty/short/all"){
            val email =
                call.principal<JWTPrincipal>()?.payload?.getClaim("email").toString().removeSurrounding("\"")
            val result = GetAllShortenUrlByEmailUseCase().getAllShortUrls(email = email)
            call.respond(
                status = HttpStatusCode(result.httpStatusCode, description = result.message),
                result
            )
        }
    }
}