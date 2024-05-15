package com.example.common

import com.typesafe.config.ConfigFactory

object RedisConfig {
    private val load = ConfigFactory.load()
    val redisUrl = load.getString("ktor.redis.redisUrl") ?: ""


}