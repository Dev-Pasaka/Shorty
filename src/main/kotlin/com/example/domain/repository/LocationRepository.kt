package com.example.domain.repository

import com.example.data.remote.dto.locationDto.LocationDto

interface LocationRepository {
    suspend fun getLocation(ipAddress:String):LocationDto
}