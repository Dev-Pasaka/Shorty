package com.example.domain.repository


interface EncryptionRepository {
    fun hashPassword(password:String):String
    fun verifyHashedPassword(password: String,hashedPassword:String):Boolean
}