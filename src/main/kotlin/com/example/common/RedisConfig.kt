package com.example.common

import com.typesafe.config.ConfigFactory

object RedisConfig {
    private val load = ConfigFactory.load()
    val redisUrl = load.getString("ktor.redis.redisUrl") ?: ""
    val port = load.getString("ktor.redis.port") ?: ""

    //Keys
    const val LANDING_PAGE_STATS = "pageStats"





}