package com.example.domain.usecase.shortenUrl

import com.example.common.ServerConfig
import com.example.data.database.entries.shortenUrl.Analytics
import com.example.data.repository.LocationRepositoryImpl
import com.example.data.repository.ShortenUrlRepositoryImpl
import com.example.domain.repository.LocationRepository
import com.example.domain.repository.ShortenUrlRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class AddClicksAndAnalyticsUseCase(
    private val shortenUrlRepository: ShortenUrlRepository = ShortenUrlRepositoryImpl(),
    private val locationRepository: LocationRepository = LocationRepositoryImpl()
) {
    suspend fun  getLocation(
        shortUrl: String,
        analytics: Analytics
    ) = withContext(Dispatchers.IO) {

        try {
            val location = try {
                val location = locationRepository.getLocation(analytics.ipAddress)
                ServerConfig.logger.info(Json.encodeToString(location))
                location
            }catch (e:Exception){
                ServerConfig.logger.warn(e.printStackTrace().toString())
                null
            }
            val allAnalytics = analytics.copy(
                serviceProvider = location?.serviceProvider,
                cityName = location?.cityName,
                countryCode = location?.countryCode,
                countryName = location?.countryName,
                ip = location?.ip,
                isProxy = location?.isProxy ?: false,
                latitude = location?.latitude,
                longitude = location?.longitude,
                timeZone = location?.timeZone,
                regionName = location?.regionName,
                zipCode = location?.zipCode,

                )

            launch {
                shortenUrlRepository.addClicks(
                    shortUrl = shortUrl,
                    analytics = allAnalytics
                )
            }

            ServerConfig.logger.info("Analytics :$allAnalytics")

        }catch (e:Exception){
            ServerConfig.logger.info(e.printStackTrace().toString())
        }

    }
}