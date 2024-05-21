package com.example.common

import com.typesafe.config.ConfigFactory

object IP2Config {
    private val load = ConfigFactory.load()
    val baseUrl = load.getString("ktor.ip2Location.baseUrl") ?: ""
    val apiKey= load.getString("ktor.ip2Location.apiKey") ?: ""
}