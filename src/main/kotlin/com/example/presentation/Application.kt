package com.example.presentation

import com.example.common.ServerConfig
import com.example.plugins.*
import eu.bitwalker.useragentutils.UserAgent
import io.ktor.client.plugins.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

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

    val properties = System.getProperties()
    properties.forEach { (key, value) ->
        println("$key: $value")
    }
    val loggerName = System.getProperty("LOGGER_NAME")
    println("LoggerName: $loggerName")

}


fun Route.analytics() {
    get("/data") {
        val ipAddress = call.request.header("X-Forwarded-For") ?: call.request.header("X-Real-IP") ?: call.request.origin.remoteHost
        val userAgentString = call.request.header("User-Agent") ?: "Unknown"
        val userAgent = UserAgent.parseUserAgentString(userAgentString)
        val browser = userAgent.browser.getName()
        val browserVersion = userAgent.browserVersion

        val os = userAgent.operatingSystem.getName()
        val deviceType = userAgent.operatingSystem.deviceType
        val deviceManufacturer = userAgent.operatingSystem.manufacturer

        val geoLocation = (ipAddress)

        call.respond(
            mapOf(
                "ip" to ipAddress,
                "browser" to browser,
                "browserVersion" to "versionName: ${browserVersion.version} version: ${browserVersion.minorVersion}",
                "deviceType" to "$deviceType",
                "manufacturer" to "$deviceManufacturer",
                "os" to os,
                "location" to ""
            )
        )
    }
}