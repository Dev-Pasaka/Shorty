package com.example.plugins

import io.ktor.http.*
import io.ktor.serialization.gson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json.Default.serializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import kotlinx.serialization.serializer

val httpStatusCodeModule = SerializersModule {
    contextual(HttpStatusCodeSerializer)
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

object HttpStatusCodeSerializer : KSerializer<HttpStatusCode> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HttpStatusCode", PrimitiveKind.INT)

    override fun deserialize(decoder: Decoder): HttpStatusCode {
        val value = decoder.decodeInt()
        return HttpStatusCode.fromValue(value) ?: throw SerializationException("Unknown HTTP status code: $value")
    }

    override fun serialize(encoder: Encoder, value: HttpStatusCode) {
        encoder.encodeInt(value.value)
    }
}