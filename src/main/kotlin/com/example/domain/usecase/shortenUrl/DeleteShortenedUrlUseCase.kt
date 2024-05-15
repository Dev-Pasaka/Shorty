package com.example.domain.usecase.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.queryResults.shortenUrl.DeleteShortenedUrlQueryResult
import com.example.data.database.queryResults.shortenUrl.GetAllShortenedUrlQueryResult
import com.example.data.repository.ShortenUrlRepositoryImpl
import com.example.domain.repository.ShortenUrlRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json



class DeleteShortenedUrlUseCase(
    private val shortenUrlRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl()
){
    suspend fun deleteShortenedUrl(shortUrl:String): DeleteShortenedUrlQueryResult = try {
        val result = shortenUrlRepository.deleteShortenedUrl(shortUrl = shortUrl)
        ServerConfig.logger.info(Json.encodeToString(result))
        result
    }catch (e:Exception){
        ServerConfig.logger.warn(Json.encodeToString(e.localizedMessage))
        DeleteShortenedUrlQueryResult(httpStatusCode = HttpStatusCode.InternalServerError.value, message = "An expected error occurred")
    }
}