package com.example.domain.model.user

import kotlinx.serialization.Serializable


@Serializable
data class UpdateUser(
    val firstName:String? = null,
    val lastName:String? = null,
    val email:String? = null,
    val phone:String? = null,
)