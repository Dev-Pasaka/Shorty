package com.example.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserCredentials(
    val email:String,
    val password:String
)