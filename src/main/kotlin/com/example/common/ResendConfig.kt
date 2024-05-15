package com.example.common

import com.typesafe.config.ConfigFactory

object ResendConfig {
    private val load = ConfigFactory.load()
    val resendBasUrl = load.getString("ktor.resend.baseUrl") ?: ""
    val apiKey = load.getString("ktor.resend.resendApiKey") ?: ""
}