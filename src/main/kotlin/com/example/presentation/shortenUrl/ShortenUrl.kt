package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.domain.usecase.shortenUrl.GetLongUrlUseCase
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.shortenUrl(){
    get("/{id}"){
        val id= call.parameters["id"]
        val shortUrl = "http://192.46.236.189:8088/$id"

        if (id.isNullOrBlank()) {
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                GetProjectQueryResults(message = "Invalid short link")
            )
        }

        /** Get quickies url */
        val result = GetLongUrlUseCase().getLongUrl(shortUrl = shortUrl ?: "")

        if (result.longUrl != null){
            call.respondRedirect(url = result.longUrl)
        }else{
            call.respond(
                status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
                message = result
            )
        }


    }
}