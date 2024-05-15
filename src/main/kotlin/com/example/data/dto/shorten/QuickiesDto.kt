package com.example.data.dto.shorten

import kotlinx.serialization.Serializable


@Serializable
data class QuickiesDto(
    val projectName:String = "Quickies",
    val longUrl:String,
)