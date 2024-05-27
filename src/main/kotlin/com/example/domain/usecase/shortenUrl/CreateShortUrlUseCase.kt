package com.example.domain.usecase.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.database.queryResults.shortenUrl.CreateShortenedUrlQueryResult
import com.example.data.repository.GenerateShortUrlCodeRepositoryImpl
import com.example.data.repository.ShortenUrlRepositoryImpl
import com.example.domain.repository.GenerateShortUrlCodeRepository
import com.example.domain.repository.ShortenUrlRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class CreateShortUrlUseCase(
    private val serverConfig:ServerConfig = ServerConfig,
    private val shortenUrlRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl(),
    private val generateShortUrlCodeRepository: GenerateShortUrlCodeRepository = GenerateShortUrlCodeRepositoryImpl()
) {
    suspend fun createShortUrl(shortenUrl: ShortenUrl):CreateShortenedUrlQueryResult = try {
        val code = generateShortUrlCodeRepository.generateShortUrlCode()

        val result = shortenUrlRepository.createShortenedUrl(shortenUrl = shortenUrl.copy(shortenUrl = code))
        ServerConfig.logger.info(Json.encodeToString(result))
        val fullUrl = "${serverConfig.baseUrl}${result.shortenedUrl?.shortUrl}"
        result.copy(shortenedUrl = result.shortenedUrl?.copy(shortUrl = fullUrl))
    }catch (e:Exception){
        e.printStackTrace()
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        CreateShortenedUrlQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}