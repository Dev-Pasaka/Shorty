package com.example.domain.model.user

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class UserData(
    val id:String = ObjectId().toString(),
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String,
)