package com.example.domain.usecase.sms

import com.example.data.remote.model.SMSBody
import com.example.data.repository.SMSRepositoryImpl
import com.example.domain.repository.SMSRepository

class SendSMSUseCase(
    private val smsRepository: SMSRepository = SMSRepositoryImpl()
) {
    suspend fun sendSms(smsBody: List<SMSBody>){
        smsRepository.sendSMS(smsBody = smsBody)
    }
}