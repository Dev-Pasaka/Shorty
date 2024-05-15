package com.example.common

import com.typesafe.config.ConfigFactory

object SendGridConfig {
    private val load = ConfigFactory.load()
    val sendGridApiKey = load.getString("ktor.sendGrid.sendGridApiKey") ?: ""
    val fromEmail = load.getString("ktor.sendGrid.fromEmail") ?: ""
}