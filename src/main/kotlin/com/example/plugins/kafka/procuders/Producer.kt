package com.example.plugins.kafka.procuders

import com.example.common.KafkaConfig
import com.example.common.ServerConfig
import com.example.data.dto.kafka.KafkaProducerResponseDto
import io.ktor.http.*
import io.ktor.server.application.*
import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun configureKafkaProducer(topic:String, message:String):KafkaProducerResponseDto {
    val producerProps = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConfig.kafkaUrl)
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
    }

    val producer = KafkaProducer<Nothing, String>(producerProps)
    val record = ProducerRecord(topic, null, message)

    return try {
        ServerConfig.logger.info("Produced topic: $topic  message: $message")
        producer.send(record)
        KafkaProducerResponseDto(status = true, message = "Your email are in queue you will receive them shortly")
    }
    catch (e:Exception){
        ServerConfig.logger.warn("Failed to produce message: $message")
        KafkaProducerResponseDto(httpStatus = HttpStatusCode.InternalServerError.value, message = "Failed to queue message:\n Error: ${e.localizedMessage}")
    }finally {
        producer.close()
    }
}