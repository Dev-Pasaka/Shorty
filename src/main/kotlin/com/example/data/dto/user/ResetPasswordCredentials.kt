package com.example.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class ResetPasswordCredentials(
    val email: String,
    val newPassword: String,
    val otpCode: String
)