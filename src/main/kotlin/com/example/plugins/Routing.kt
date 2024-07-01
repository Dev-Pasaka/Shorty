package com.example.plugins

import com.example.common.ServerConfig
import com.example.presentation.analytics
import com.example.presentation.auth.*
import com.example.presentation.homePage.getHomePageStats
import com.example.presentation.project.*
import com.example.presentation.shortenUrl.*
import com.example.presentation.user.getUserData
import com.example.presentation.user.updateUserInfo
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.MarkerFactory

fun Application.configureRouting() {

    routing {
        intercept(ApplicationCallPipeline.Monitoring) {
            val requestTime = System.currentTimeMillis()
            proceed()
            val responseTime = System.currentTimeMillis()
            val duration = responseTime - requestTime
            val statusCode = call.response.status()?.value ?: -1
            val method = call.request.httpMethod.value

            /** Http Methods logs colors */
            val reset = "\u001B[0m"
            val green = "\u001B[32m"
            val cyan = "\u001B[36m"
            val magenta = "\u001B[35m"
            val yellow = "\u001B[33m"
            val red = "\u001B[31m"

            val methodColor = when (method) {
                "GET" -> green
                "POST" -> cyan
                "PUT" -> magenta
                "DELETE" -> yellow
                else -> red
            }
            val coloredMethod = "$methodColor$method$reset"

            val statusColor = if (statusCode == 200) green else red
            val coloredStatus = "$statusColor$statusCode$reset"
            val logMessage = "[$coloredMethod] HTTP status: $coloredStatus >> $duration ms"
            ServerConfig.logger.info(logMessage)

        }

        get("/") {

            call.respondText("")
        }
        get("/new") {

            call.respondText("Hello there new endpoint")
        }

        //home
        getHomePageStats()

        //Auth
        createAccount()
        deleteUser()
        passwordResetOtp()
        resetPassword()
        signIn()
        verifyAccount()


        //User
        getUserData()
        updateUserInfo()

        //Project
        createProject()
        deleteProject()
        getAllProjects()
        getApiKey()
        resetApiKey()
        getProject()

        //Shorten Url
        shortenUrl()
        deleteShortUrl()
        generateShortUrl()
        getLongUrl()
        getShortUrls()
        quickies()
        shortenUrl()
        updateLongUrl()
        getAllShortUrls()
        analytics()


    }
}
