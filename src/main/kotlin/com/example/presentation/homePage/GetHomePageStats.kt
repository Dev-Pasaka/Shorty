package com.example.presentation.homePage

import com.example.common.ServerConfig
import com.example.domain.usecase.homePage.GetLandingPageStatsUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getHomePageStats() {
    get("${ServerConfig.apiVersion}/home/stats"){
        val result = GetLandingPageStatsUseCase().getLandingPageStats()
        call.respond(
            status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
            message = result
        )
    }
}