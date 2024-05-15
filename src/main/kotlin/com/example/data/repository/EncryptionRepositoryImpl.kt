package com.example.data.repository

import com.example.data.database.Entries
import com.example.data.database.entries.user.User
import com.example.data.database.entries.user.encryptPassword
import com.example.data.database.queryResults.user.SignInUserQueryResult
import com.example.domain.repository.EncryptionRepository
import com.mongodb.client.model.Filters
import org.mindrot.jbcrypt.BCrypt

class EncryptionRepositoryImpl(): EncryptionRepository {
    override fun hashPassword(password: String): String  =  BCrypt.hashpw(password, BCrypt.gensalt())

    override fun verifyHashedPassword(password:String,hashedPassword: String): Boolean  = BCrypt.checkpw(password, hashedPassword)
}

