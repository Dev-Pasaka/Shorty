package com.example.domain.repository

import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.database.queryResults.shortenUrl.*

interface ShortenUrlRepository {
    suspend fun createShortenedUrl(shortenUrl: ShortenUrl):CreateShortenedUrlQueryResult
    suspend fun getAllShortenedUrls(projectName:String):GetAllShortenedUrlQueryResult
    suspend fun getLongUrl(shortUrl:String):GetLongUrlQueryResult
    suspend fun deleteShortenedUrl(shortUrl: String): DeleteShortenedUrlQueryResult
    suspend fun updateLongUrl(shortUrl: String, longUrl:String):UpdateLongQueryResult
}