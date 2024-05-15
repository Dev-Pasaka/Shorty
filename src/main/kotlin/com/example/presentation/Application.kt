package com.example.presentation

import com.example.common.ServerConfig
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = ServerConfig.port.toInt(), host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSockets()
    configureAdministration()
    configureSerialization()
    configureHTTP()
    configureSecurity()
    configureRouting()
    corsConfiguration()
    requestLogging()
}
