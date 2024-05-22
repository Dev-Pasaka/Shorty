package com.example.domain.usecase.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.queryResults.shortenUrl.GetAllShortenedUrlQueryResult
import com.example.data.database.queryResults.shortenUrl.GetLongUrlQueryResult
import com.example.data.repository.RedisRepositoryImpl
import com.example.data.repository.ShortenUrlRepositoryImpl
import com.example.domain.repository.RedisRepository
import com.example.domain.repository.ShortenUrlRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


class GetLongUrlUseCase(
    private val shortenUrlRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl(),
    private val redisRepository: RedisRepository = RedisRepositoryImpl()
){
    suspend fun getLongUrl(shortUrl:String): GetLongUrlQueryResult = try {
        val getUrlFromRedis = redisRepository.get(shortUrl)
        val result = if(getUrlFromRedis !=null) Json.decodeFromString<GetLongUrlQueryResult>(getUrlFromRedis)
        else{
            val result = shortenUrlRepository.getLongUrl(shortUrl = shortUrl)
            redisRepository.set(key = shortUrl, value = Json.encodeToString(result), expireAt = (60*5) )
            result
        }
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        GetLongUrlQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}