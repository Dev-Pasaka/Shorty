package com.example.domain.usecase.kafka

import com.example.common.KafkaConfig
import com.example.common.ServerConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.consumer.KafkaConsumer
import java.time.Duration
import java.util.*

class EmailConsumerUseCase(

) {
    fun configureKafkaConsumer(topic: String = ""): Flow<String> = flow {
        val props = Properties().apply {
            put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.kafkaUrl)
            put(ConsumerConfig.GROUP_ID_CONFIG, "test-group")
            put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
            put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
        }
        val consumer = KafkaConsumer<String, String>(props)
        consumer.subscribe(listOf(topic))

        try {
            while (true) {
                val records: ConsumerRecords<String, String> = consumer.poll(Duration.ofMillis(100))
                records.forEach { record ->
                    ServerConfig.logger.info("Consumed topic:${record.topic()} value: ${record.value()}")
                    emit(record.value())
                }
            }
        } finally {
            consumer.close()
        }

    }

}