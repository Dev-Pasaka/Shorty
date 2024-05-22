package com.example.domain.repository

interface RedisRepository {
    suspend fun get(key:String):String?
    suspend fun set(key: String, value:String, expireAt:Long):Boolean
}