package com.example.domain.repository

import com.africastalking.AfricasTalking
import com.africastalking.SmsService
import com.example.common.AfricasTalkingConfig
import com.example.data.remote.dto.smsDto.SmsDto
import com.example.data.remote.model.SMSBody

interface SMSRepository {
    suspend fun sendSMS(smsBody: List<SMSBody>)
}

