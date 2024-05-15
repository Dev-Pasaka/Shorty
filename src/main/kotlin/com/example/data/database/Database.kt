package com.example.data.database

import com.example.common.DatabaseConfig
import com.example.common.ServerConfig
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase

object Database {

    /** Initialize MongoDb Client */
    private val mongoClient = MongoClients.create(DatabaseConfig.databaseUrl)

    /** Database Instantiation of client*/
    val database: MongoDatabase = mongoClient.getDatabase(DatabaseConfig.databaseName)
    fun closeDb() = mongoClient.close()
}
