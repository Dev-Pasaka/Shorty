package com.example.data.database.entries.sms

import kotlinx.serialization.Serializable
import org.bson.types.ObjectId

@Serializable
data class SMS(
    val id:String = ObjectId().toString(),
    val toPhone:String,
    val message:String,
    val deliveryStatus:SMSDeliveryStatus = SMSDeliveryStatus.PENDING,
    val createdAt:String,
    val deliveredAt:String
)
