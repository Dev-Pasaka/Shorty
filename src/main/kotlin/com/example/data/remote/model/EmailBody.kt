package com.example.data.remote.model

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class EmailBody(
    val id:String = ObjectId().toString(),
    val from:String,
    val to :String,
    val subject:String,
    val text:String
)
