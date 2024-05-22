package com.example.data.repository

import com.example.data.redis.Redis
import com.example.domain.repository.RedisRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RedisRepositoryImpl(
    private val redis:Redis = Redis
):RedisRepository {
    override suspend fun get(key: String): String? = withContext(Dispatchers.IO) {
        try {
            redis.jedis.get(key)
        }catch (e:Exception){null}
    }

    override suspend fun set(key: String, value: String, expireAt:Long): Boolean = withContext(Dispatchers.IO) {
       try {
           redis.jedis.set(key, value) ?: return@withContext false
           redis.jedis.expire(key,expireAt )
           true
       }catch (e:Exception){
           false
       }
    }
}

