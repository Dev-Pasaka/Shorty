package com.example.common

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import org.slf4j.*

object ServerConfig{

    private val load = ConfigFactory.load()
    val port = System.getenv("SERVER_PORT") ?: load.getString("ktor.deployment.port") ?: "8080"
    val logger: Logger =  LoggerFactory.getLogger(
        System.getenv("LOGGER_NAME") ?:load.getString("ktor.server.logger") ?: ""
    )
    val apiVersion = System.getenv("API_VERSION") ?: load.getString("ktor.server.apiVersion") ?: ""
    val baseUrl = System.getenv("BASE_URL") ?: load.getString("ktor.server.baseUrl") ?: ""

}