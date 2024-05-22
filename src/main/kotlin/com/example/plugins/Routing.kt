package com.example.plugins

import com.example.presentation.analytics
import com.example.presentation.auth.*
import com.example.presentation.homePage.getHomePageStats
import com.example.presentation.project.*
import com.example.presentation.shortenUrl.*
import com.example.presentation.user.getUserData
import com.example.presentation.user.updateUserInfo
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        get("/") {

            call.respondText("")
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
