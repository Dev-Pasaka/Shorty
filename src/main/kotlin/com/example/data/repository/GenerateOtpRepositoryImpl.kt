package com.example.data.repository

import com.example.domain.repository.GenerateOtpCodeRepository
import java.security.SecureRandom

class GenerateOtpRepositoryImpl():GenerateOtpCodeRepository {
    override fun generateOtpCode(): String {
        val random = SecureRandom()
        val bytes = ByteArray(6)
        random.nextBytes(bytes)
        return  bytes.map { it.toInt() and 6 }.joinToString("")
    }
}