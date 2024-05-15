package com.example.data.repository

import com.example.domain.repository.GenerateApiKey
import org.bson.types.ObjectId

class GenerateApiKeyImpl(): GenerateApiKey {
    override fun generateApiKey(): String  = ObjectId().toString()
}