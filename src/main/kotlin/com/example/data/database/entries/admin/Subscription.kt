package com.example.data.database.entries.admin

import com.example.data.database.Entries
import org.bson.types.ObjectId


data class Subscription(
    val id:String = ObjectId().toString(),
    val adminEmail:String = "dev.pasaka@gmail.com",
    val sandBoxPrepaidCredits:Int = 15000,
    val sandBoxDailyQuota:Int = 500,
    val livePrepaidCredits:Int = 1000,
    val postPayCredits:Int = 0,
    val postPayCreditRate:Double = 2.0,
    val prePayCreditRate:Double = 2.5,
    val prePayIncrementFactor:Int = 1,
    val postPayIncrementFactor:Int = 1

)

