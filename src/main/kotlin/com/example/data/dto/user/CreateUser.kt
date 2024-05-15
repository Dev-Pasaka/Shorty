package com.example.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class CreateUser(
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String,
    val password:String,
)