package com.example.plugins

import com.example.presentation.auth.createAccount
import com.example.presentation.auth.deleteUser
import com.example.presentation.auth.resetPassword
import com.example.presentation.auth.signIn
import com.example.presentation.email.sendEmails
import com.example.presentation.project.*
import com.example.presentation.sms.sendSms
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

        //Auth
        createAccount()
        deleteUser()
        resetPassword()
        signIn()

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

        //Emails
        sendEmails()

        //Sms
        sendSms()

    }
}
