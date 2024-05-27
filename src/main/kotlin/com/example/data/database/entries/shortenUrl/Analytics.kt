package com.example.data.database.entries.shortenUrl

import com.example.common.utils.dateAndTimeUtils.DateAndTimeUtils

import kotlinx.serialization.Serializable
import org.apache.kafka.common.protocol.types.Field.Str

@Serializable
data class Analytics(
    val ipAddress:String,
    val browser:String,
    val browserVersion:String,
    val deviceType:String,
    val manufacturer: String,
    val operatingSystem:String,

    val serviceProvider: String? = null,
    val cityName: String? = null,
    val countryCode: String? = null,
    val countryName: String? = null,
    val ip: String? = null,
    val isProxy: Boolean = false,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val regionName: String? = null,
    val timeZone: String? = null,
    val zipCode: String? = null,

    val createdAt:String? = DateAndTimeUtils.currentTime()
)
