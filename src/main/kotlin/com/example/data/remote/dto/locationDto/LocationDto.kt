package com.example.data.remote.dto.locationDto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LocationDto(
    @SerialName("as") val serviceProvider: String,
    @SerialName("asn") val asn: String,
    @SerialName("city_name") val cityName: String,
    @SerialName("country_code") val countryCode: String,
    @SerialName("country_name") val countryName: String,
    @SerialName("ip") val ip: String,
    @SerialName("is_proxy") val isProxy: Boolean,
    @SerialName("latitude") val latitude: Double,
    @SerialName("longitude") val longitude: Double,
    @SerialName("region_name") val regionName: String,
    @SerialName("time_zone") val timeZone: String,
    @SerialName("zip_code") val zipCode: String
)