package com.example.common

import com.typesafe.config.ConfigFactory
import io.github.cdimascio.dotenv.Dotenv


object JWTConfig {
    private val load = ConfigFactory.load()
    val jwtAudience = load.getString("ktor.jwt.audience") ?: ""
    val jwtDomain = load.getString("ktor.jwt.domain") ?: ""
    val jwtRealm:String = load.getString("ktor.jwt.realm") ?: ""
    val jwtSecret = load.getString("ktor.jwt.secrete") ?: ""
}