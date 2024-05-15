package com.example.data.database.entries.emails

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class Email(
    val id:String = ObjectId().toString(),
    val from:String,
    val to:String,
    val subject: String,
    val message:String,
    val deliveryStatus: DeliveryStatus = DeliveryStatus.PENDING,
    val createdAt:String,
    val deliveredAt:String? = null
    )
