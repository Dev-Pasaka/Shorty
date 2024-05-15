package com.example.common

import com.typesafe.config.ConfigFactory

object AfricasTalkingConfig {
    private val load = ConfigFactory.load()

    val apiKey = load.getString("ktor.africasTalking.africasTalkingApiKey") ?: ""
    val username = load.getString("ktor.africasTalking.africasTalkingUsername") ?: ""
}