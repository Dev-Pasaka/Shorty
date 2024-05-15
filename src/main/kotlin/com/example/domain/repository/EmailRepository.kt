package com.example.domain.repository

import com.example.data.database.entries.emails.DeliveryStatus
import com.example.data.database.entries.emails.Email
import com.example.data.remote.dto.SendEmailDto
import com.example.data.remote.dto.bulkEmailDto.SendBulkEmailDto
import com.example.data.remote.model.EmailBody

interface EmailRepository {

    suspend fun sendEmailRepository(emailBody:EmailBody):SendEmailDto
    suspend fun sendBulkEmailRepository(emailBody:List<EmailBody>):SendBulkEmailDto
    suspend fun createEmailRecord(email: Email):String
    suspend fun updateEmailRecord(emailId:String, deliveryStatus: DeliveryStatus, updateDeliveredTime:Boolean = false):Boolean

}