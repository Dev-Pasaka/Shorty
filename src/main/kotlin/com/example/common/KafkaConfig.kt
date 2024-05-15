package com.example.common

import com.typesafe.config.ConfigFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext

object KafkaConfig {
    private val load = ConfigFactory.load()

    val kafkaUrl = load.getString("ktor.kafka.kafkaUrl") ?: ""
    //val EMAIL_CONSUMER_THREAD = newSingleThreadContext("Emails")
    //val SMS_CONSUMER_THREAD = newSingleThreadContext("SMS")
    const val EMAIL_TOPIC = "email"
    const val SMS_TOPIC = "sms"

}