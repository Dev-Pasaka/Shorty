package com.example.data.remote.dto.bulkEmailDto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BulkEmailResponse(
    @SerialName("data")
    val `data`: List<DataX>
)