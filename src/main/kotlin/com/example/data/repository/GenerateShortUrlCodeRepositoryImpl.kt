package com.example.data.repository

import com.example.data.database.Entries
import com.example.domain.repository.GenerateShortUrlCodeRepository
import java.security.SecureRandom

class GenerateShortUrlCodeRepositoryImpl(
) : GenerateShortUrlCodeRepository {
    override fun generateShortUrlCode(): String {
        val generatedStrings = mutableSetOf<String>()
        val secureRandom = SecureRandom()
        val charPool = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz"

        var uniqueString: String
        do {
            uniqueString = (1..6)
                .map { charPool[secureRandom.nextInt(charPool.length)] }
                .joinToString("")
        } while (generatedStrings.contains(uniqueString))

        generatedStrings.add(uniqueString)
        return uniqueString

    }

}

