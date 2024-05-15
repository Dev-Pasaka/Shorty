package com.example.plugins.kafka.consumers

import com.example.common.KafkaConfig
import com.example.common.ServerConfig
import io.ktor.server.application.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*




