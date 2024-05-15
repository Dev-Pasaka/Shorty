package com.example.common

import com.typesafe.config.ConfigFactory

object DatabaseConfig {
    private val load = ConfigFactory.load()
    val databaseUrl = load.getString("ktor.mongoDB.mongoDBUrl") ?: ""
    val databaseName = load.getString("ktor.mongoDB.databaseName") ?: ""
}