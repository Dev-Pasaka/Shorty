package com.example.common

import com.typesafe.config.ConfigFactory

object PaviconTechCommConfig {
    private val load = ConfigFactory.load()
    val apiKey = load.getString("ktor.paviconTechComm.apiKey") ?: ""
    val fromEmail = load.getString("ktor.paviconTechComm.fromEmail") ?: ""
    val baseUrl = load.getString("ktor.paviconTechComm.baseUrl") ?: ""
    val emailEndpoint = load.getString("ktor.paviconTechComm.emailEndPoint") ?: ""
}