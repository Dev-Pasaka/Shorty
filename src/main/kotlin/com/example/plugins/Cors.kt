package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.corsConfiguration(){
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)

        allowCredentials = true

        allowHost("127.0.0.1:5500", schemes = listOf("http"))
        allowHost("localhost:5500", schemes = listOf("http"))
        allowHost("localhost:3000", schemes = listOf("http"))  // Adding your requested origin

        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.AccessControlAllowHeaders)
        allowHeader(HttpHeaders.AccessControlAllowOrigin)

        anyHost()
        allowHeader("key")

    }
}