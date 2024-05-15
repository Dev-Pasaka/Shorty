package com.example.data.repository

import com.africastalking.AfricasTalking
import com.africastalking.SmsService
import com.example.common.AfricasTalkingConfig
import com.example.common.ServerConfig
import com.example.data.remote.dto.smsDto.SmsDto
import com.example.data.remote.model.SMSBody
import com.example.domain.repository.SMSRepository

class SMSRepositoryImpl():SMSRepository {
    override suspend fun sendSMS(smsBody: List<SMSBody>) {
        smsBody.forEach {
            AfricasTalking.initialize(AfricasTalkingConfig.username, AfricasTalkingConfig.apiKey)
            val sms = AfricasTalking.getService<SmsService>(AfricasTalking.SERVICE_SMS)
            try {
                val response = sms.send(it.message, arrayOf(it.toRecipient), true)
                ServerConfig.logger.info("AIT Response: $response")
            } catch (e: Exception) {
                ServerConfig.logger.warn("AIT Response: ${e.localizedMessage}")
            }
        }
    }
}