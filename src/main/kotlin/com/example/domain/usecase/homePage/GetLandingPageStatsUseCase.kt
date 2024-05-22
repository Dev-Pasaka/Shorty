package com.example.domain.usecase.homePage

import com.example.common.RedisConfig
import com.example.common.ServerConfig
import com.example.data.database.queryResults.shortenUrl.GetLandingPageQueryResults
import com.example.data.repository.LandingPageStatsRespositoryImpl
import com.example.data.repository.RedisRepositoryImpl
import com.example.domain.repository.LandingPageStatsRepository
import com.example.domain.repository.RedisRepository
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class GetLandingPageStatsUseCase(
    private val landingPageStats: LandingPageStatsRepository = LandingPageStatsRespositoryImpl(),
    private val redisRepository: RedisRepository = RedisRepositoryImpl()
) {

    suspend fun getLandingPageStats() = try {

        val redis = redisRepository.get(RedisConfig.LANDING_PAGE_STATS)
        if (redis != null) Json.decodeFromString<GetLandingPageQueryResults>(redis)
        else {
            val result = landingPageStats.getLandingPageStats()
            ServerConfig.logger.info("Landing page stats: $result")
            redisRepository.set(
                key = RedisConfig.LANDING_PAGE_STATS,
                value = Json.encodeToString(result),
                expireAt = 3600
            )
            result
        }
    } catch (e: Exception) {
        ServerConfig.logger.info("Landing page stats: ${e.localizedMessage}")
        GetLandingPageQueryResults(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            message = "An expected error occurred"
        )

    }
}