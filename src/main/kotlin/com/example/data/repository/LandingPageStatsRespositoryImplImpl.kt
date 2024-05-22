package com.example.data.repository

import com.example.data.database.Entries
import com.example.data.database.queryResults.shortenUrl.GetLandingPageQueryResults
import com.example.data.database.queryResults.shortenUrl.LandingPageData
import com.example.domain.repository.LandingPageStatsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class LandingPageStatsRespositoryImpl(
    val entries:Entries = Entries
):LandingPageStatsRepository {
    override suspend fun getLandingPageStats(): GetLandingPageQueryResults = withContext(Dispatchers.IO) {
        val users = entries.dbUser.find().count()
        val shortenedUrls = entries.dbShortenUrl.find().count()
        val clicks = entries.dbShortenUrl.find().map { it.clicks }.sumOf { it }

        return@withContext GetLandingPageQueryResults(
            status = true,
            message = "Success",
            data = LandingPageData(
                shortenedUrls = shortenedUrls,
                clicks = clicks,
                activeUsers = users
            )
        )

    }

}

