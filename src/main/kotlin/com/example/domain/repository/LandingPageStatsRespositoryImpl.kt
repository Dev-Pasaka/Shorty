package com.example.domain.repository

import com.example.data.database.queryResults.shortenUrl.GetLandingPageQueryResults

interface LandingPageStatsRepository {
    suspend fun getLandingPageStats():GetLandingPageQueryResults
}