package com.example.plugins

import com.example.common.ServerConfig
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import io.ktor.util.*
import org.slf4j.LoggerFactory
import kotlin.system.measureTimeMillis

fun Application.requestLogging() {
    environment.monitor.subscribe(ApplicationStopped) { application ->
        application.environment.log.info("Server is stopped")
        application.environment.monitor.unsubscribe(ApplicationStarted) {
            application.log.info("Server is started")

        }
        application.environment.monitor.unsubscribe(ApplicationStopped) {
            application.log.info("Server is stopped")
        }
    }
}