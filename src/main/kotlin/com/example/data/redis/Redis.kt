package com.example.data.redis

import com.example.common.RedisConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool

object Redis {
  private var pool = JedisPool(RedisConfig.redisUrl)
  val jedis: Jedis = pool.resource


}