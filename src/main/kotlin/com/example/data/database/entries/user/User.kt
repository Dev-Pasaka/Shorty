package com.example.data.database.entries.user

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.bson.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

@Serializable
data class User(
    val id:String = ObjectId().toString(),
    val firstName:String,
    val lastName:String,
    val email:String,
    val phone:String,
    val password:String,
    val otpCode:String? = null
){
    fun toDocument(): Document = Document.parse(Json.encodeToString(this))

    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        fun fromDocument(document: Document): User = json.decodeFromString(document.toJson())
    }
}