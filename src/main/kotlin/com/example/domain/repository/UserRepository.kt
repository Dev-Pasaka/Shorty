package com.example.domain.repository

import com.example.data.database.entries.user.User
import com.example.data.database.queryResults.shortenUrl.SendOtpQueryResult
import com.example.data.database.queryResults.user.*
import com.example.domain.model.user.UpdateUser
import io.ktor.server.application.*

interface UserRepository {
    suspend fun createUser(user: User): CreateUserQueryResult

    suspend fun verifyUserAccount(email:String, otpCode: String): VerifyAccountQueryResult
    suspend fun signIn(email:String, password:String): SignInUserQueryResult
    suspend fun updateUser(email: String, user: UpdateUser): UpdateUserQueryResult
    suspend fun deleteUser(email: String): DeleteUserQueryResult
    suspend fun getUser(email:String): GetUserQueryResult
    suspend fun resetPassword(email:String, newPassword:String, otpCode: String):ResetUserPasswordQueryResult

    suspend fun sendOtp(email:String):SendOtpQueryResult

}