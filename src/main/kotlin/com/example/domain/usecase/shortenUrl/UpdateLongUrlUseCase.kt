package com.example.domain.usecase.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.queryResults.shortenUrl.GetAllShortenedUrlQueryResult
import com.example.data.database.queryResults.shortenUrl.UpdateLongQueryResult
import com.example.data.repository.ShortenUrlRepositoryImpl
import com.example.domain.repository.ShortenUrlRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



class UpdateLongUrlUseCase(
    private val shortenUrlRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl()
){
    suspend fun updateLongUrl(shortUrl:String, longUrl:String): UpdateLongQueryResult = try {
        val result = shortenUrlRepository.updateLongUrl(shortUrl = shortUrl, longUrl = longUrl)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        UpdateLongQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}