package com.example.data.repository

import com.example.data.database.Entries
import com.example.data.database.entries.shortenUrl.ShortenUrl
import com.example.data.database.queryResults.shortenUrl.*
import com.example.domain.repository.GenerateShortUrlCodeRepository
import com.example.domain.repository.ShortenUrlRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ShortenUrlRepositoryImpl(
    private val entries: Entries = Entries,
    private val generateShortUrlCodeRepository: GenerateShortUrlCodeRepository = GenerateShortUrlCodeRepositoryImpl()
) : ShortenUrlRepository {
    override suspend fun createShortenedUrl(shortenUrl: ShortenUrl): CreateShortenedUrlQueryResult =
        withContext(Dispatchers.IO) {

            /** Check if similar shor url exist */
            /** Check if similar shor url exist */
            val doesSimilarShortUrlExists =
                entries.dbShortenUrl.find(
                    Filters.eq(ShortenUrl::shortenUrl.name, shortenUrl.shortenUrl)
                ).firstOrNull()

            val shortCode = if (doesSimilarShortUrlExists != null)
                generateShortUrlCodeRepository.generateShortUrlCode() else shortenUrl.shortenUrl

            val shortUrl = "http://192.46.236.189:8088/$shortCode"

            /** Create shortened url*/
            val result = entries.dbShortenUrl.insertOne(shortenUrl.copy(shortenUrl = shortUrl)).wasAcknowledged()

            return@withContext when (result) {
                true -> {
                    CreateShortenedUrlQueryResult(
                        status = true,
                        message = "Url shortened successful",
                        shortenedUrl = shortenUrl.toGetShortenUrl().copy(shortUrl = shortUrl)
                    )
                }

                false -> {
                    CreateShortenedUrlQueryResult(message = "Failed to shorten url")
                }
            }
        }

    override suspend fun getAllShortenedUrls(projectName: String): GetAllShortenedUrlQueryResult =
        withContext(Dispatchers.IO) {
            val result = entries.dbShortenUrl.find(Filters.eq(ShortenUrl::projectName.name, projectName))
                .map { it.toGetShortenUrl() }.toList()
            return@withContext if (result.isNotEmpty()) {
                GetAllShortenedUrlQueryResult(
                    status = true,
                    message = "Url fetched successfully",
                    shortenUrls = result
                )
            } else {
                GetAllShortenedUrlQueryResult(message = "You have no short links or project name provided does not exists")
            }
        }



    override suspend fun getLongUrl(shortUrl: String): GetLongUrlQueryResult = withContext(Dispatchers.IO) {
        val result = entries.dbShortenUrl.find(Filters.eq(ShortenUrl::shortenUrl.name, shortUrl)).firstOrNull()
            ?: return@withContext  GetLongUrlQueryResult(message = "Short url provide does not exist")
        return@withContext GetLongUrlQueryResult(
            status = true,
            message = "Url fetched successfully",
            data  = result.toGetShortenUrl()
        )

    }

    override suspend fun deleteShortenedUrl(shortUrl: String): DeleteShortenedUrlQueryResult  = withContext(Dispatchers.IO){
        entries.dbShortenUrl.findOneAndDelete(Filters.eq(ShortenUrl::shortenUrl.name, shortUrl))
            ?: return@withContext  DeleteShortenedUrlQueryResult(message = "Short url provided does not exists")
        return@withContext DeleteShortenedUrlQueryResult(status = true, message = "Short url deleted successfully")
    }

    override suspend fun updateLongUrl(shortUrl: String, longUrl: String): UpdateLongQueryResult  = withContext(Dispatchers.IO){
        entries.dbShortenUrl.findOneAndUpdate(
            Filters.eq(ShortenUrl::shortenUrl.name, shortUrl),
            Updates.set(ShortenUrl::longUrl.name, longUrl)
        ) ?: return@withContext UpdateLongQueryResult(message = "Short url provided does not exist")

        return@withContext UpdateLongQueryResult(
            status = true,
            message = "Url updated successful"
        )
    }

    override suspend fun getAllUrls(email: String): GetAllShortenedUrlQueryResult = withContext(Dispatchers.IO) {
        val result = entries.dbShortenUrl.find(Filters.eq(ShortenUrl::email.name, email))
            .map { it.toGetShortenUrl() }.toList()
        return@withContext if (result.isNotEmpty()) {
            GetAllShortenedUrlQueryResult(
                status = true,
                message = "Url fetched successfully",
                shortenUrls = result
            )
        } else {
            GetAllShortenedUrlQueryResult(message = "You have no short links or project name provided does not exists")
        }
    }


}