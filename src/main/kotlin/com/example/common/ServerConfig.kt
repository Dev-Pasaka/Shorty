package com.example.common

import com.typesafe.config.ConfigFactory
import org.slf4j.LoggerFactory
import org.slf4j.*

object ServerConfig{

    private val load = ConfigFactory.load()
    val port = load.getString("ktor.deployment.port") ?: "8080"
    val logger: Logger =  LoggerFactory.getLogger(load.getString("ktor.server.logger") ?: "")
    val apiVersion = load.getString("ktor.server.apiVersion") ?: ""

}