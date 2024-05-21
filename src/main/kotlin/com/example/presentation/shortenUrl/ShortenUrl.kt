package com.example.presentation.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.Analytics
import com.example.data.database.queryResults.project.GetProjectQueryResults
import com.example.domain.usecase.shortenUrl.AddClicksAndAnalyticsUseCase
import com.example.domain.usecase.shortenUrl.GetLongUrlUseCase
import eu.bitwalker.useragentutils.UserAgent
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.shortenUrl(){
    get("/{id}"){


        /** Get device and network information */
        val ipAddress = call.request.header("X-Forwarded-For") ?: call.request.header("X-Real-IP") ?: call.request.origin.remoteHost
        val userAgentString = call.request.header("User-Agent") ?: "Unknown"
        val userAgent = UserAgent.parseUserAgentString(userAgentString)
        val browser = userAgent.browser.getName()
        val browserVersion = userAgent.browserVersion

        val os = userAgent.operatingSystem.getName()
        val deviceType = userAgent.operatingSystem.deviceType
        val deviceManufacturer = userAgent.operatingSystem.manufacturer

        val id= call.parameters["id"]



        if (id.isNullOrBlank()) {
            call.respond(
                status = HttpStatusCode(HttpStatusCode.BadRequest.value, description = "Bad request"),
                GetProjectQueryResults(message = "Invalid short link")
            )
        }

        /** Get quickies url */
        val result = GetLongUrlUseCase().getLongUrl(shortUrl = id ?: "")

        if (result.data?.longUrl != null){
            /** Add analytics */
            AddClicksAndAnalyticsUseCase().getLocation(
                shortUrl = id ?: "",
                analytics = Analytics(
                    ipAddress = ipAddress,
                    browser = browser,
                    browserVersion = try {browserVersion.majorVersion}catch (e:Exception){"Not found"},
                    deviceType = deviceType.name ,
                    operatingSystem = os,
                    manufacturer = deviceManufacturer.name,
                )
            )
            call.respondRedirect(url = result.data.longUrl)
        }else{
            call.respond(
                status = HttpStatusCode(value = result.httpStatusCode, description = result.message),
                message = result
            )
        }


    }
}