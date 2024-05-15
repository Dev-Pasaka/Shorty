package com.example.data.repository

import arrow.fx.coroutines.Use
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC256
import com.example.common.JWTConfig
import com.example.common.utils.DatabaseConfig
import com.example.data.database.Entries
import com.example.data.database.entries.user.User
import com.example.data.database.entries.user.encryptPassword
import com.example.data.database.entries.user.toUserData
import com.example.data.database.queryResults.user.*
import com.example.domain.model.user.UpdateUser
import com.example.domain.repository.EncryptionRepository
import com.example.domain.repository.UserRepository
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mindrot.jbcrypt.BCrypt

import java.util.*

class UserRepositoryImpl(
    private val encryptionRepository: EncryptionRepository = EncryptionRepositoryImpl(),
    private val entries: Entries = Entries
) : UserRepository {

    override suspend fun createUser(user: User): CreateUserQueryResult = withContext(Dispatchers.IO) {

        /**Check if user exists*/
        val doesUserExists = entries.dbUser.find(
            Filters.eq(User::email.name, user.email)
        ).firstOrNull()
        if (doesUserExists != null) return@withContext CreateUserQueryResult(
            status = false,
            message = "User already exists"
        )

        /** If user does not exist create user*/
        entries.dbUser.insertOne(user.encryptPassword())
            ?: CreateUserQueryResult(
                httpStatusCode = HttpStatusCode.InternalServerError.value,
                status = false,
                message = "Failed to create user"
            )

        return@withContext CreateUserQueryResult(status = true, message = "User created successfully")
    }

    override suspend fun signIn(email: String, password: String): SignInUserQueryResult = withContext(Dispatchers.IO) {

        /** Check if  email is valid*/
        val user = entries.dbUser.find(
            Filters.eq(User::email.name, email)
        ).firstOrNull()
            ?: return@withContext SignInUserQueryResult(
                status = false,
                message = "Wrong email or password",
            )
        println(user)

        /** Get user password and Check if it's valid */
        val isPasswordValid = EncryptionRepositoryImpl().verifyHashedPassword(password, user.password)
        println("Is password valid: $isPasswordValid")

        if (!isPasswordValid) return@withContext SignInUserQueryResult(
            status = false,
            message = "Wrong  password",
            token = ""
        )

        val token = JWT.create()
            .withAudience(JWTConfig.jwtAudience)
            .withIssuer(JWTConfig.jwtDomain)
            .withClaim("email", email)
            .withExpiresAt(Date(System.currentTimeMillis() + (60000 * 3600)))
            .sign(HMAC256(JWTConfig.jwtSecret))

        return@withContext SignInUserQueryResult(status = true, message = "Sign in was successful", token = token)
    }

    override suspend fun updateUser(email: String, user: UpdateUser): UpdateUserQueryResult =
        withContext(Dispatchers.IO) {


            /** Check if user account exits */
            val getUser = entries.dbUser.find(Filters.eq(User::email.name, email)).firstOrNull()
                ?: return@withContext UpdateUserQueryResult(
                    status = false,
                    message = "User account not found"
                )

            /** Update user account information */
            val filters = Filters.eq(User::email.name, email)
            val updates = listOf(
                Updates.set(User::firstName.name, user.firstName ?: getUser.firstName),
                Updates.set(User::lastName.name, user.lastName ?: getUser.lastName),
                Updates.set(User::email.name, user.email ?: getUser.email),
                Updates.set(User::phone.name, user.phone ?: getUser.phone)
            )
            entries.dbUser.findOneAndUpdate(filters, updates) ?: return@withContext UpdateUserQueryResult(
                httpStatusCode = HttpStatusCode.InternalServerError.value,
                status = false,
                message = "Failed to update user"
            )
            return@withContext UpdateUserQueryResult(status = true, message = "User updated successfully")

        }

    override suspend fun deleteUser(email: String): DeleteUserQueryResult = withContext(Dispatchers.IO) {
        /** Check if user account exits */
        entries.dbUser.findOneAndDelete(Filters.eq(User::email.name, email))
            ?: return@withContext DeleteUserQueryResult(
                status = false,
                message = "Failed to delete user:User not found"
            )

        /** Delete user account  */
        return@withContext DeleteUserQueryResult(status = true, message = "User was deleted successfully")
    }

    override suspend fun getUser(email: String): GetUserQueryResult = withContext(Dispatchers.IO) {
        val userData = entries.dbUser.find(Filters.eq(User::email.name, email)).firstOrNull()
            ?: return@withContext GetUserQueryResult(
                status = false,
                message = "User not found, kindly create an account",
                userData = null
            )

        return@withContext GetUserQueryResult(
            status = true,
            message = "User data successfully fetched",
            userData = userData.toUserData()
        )
    }

    override suspend fun resetPassword(
        email: String,
        newPassword: String,
        otpCode: String
    ): ResetUserPasswordQueryResult = withContext(Dispatchers.IO) {


        /** Dose user account exits */
        val getUser = entries.dbUser.find(Filters.eq(User::email.name, email)).firstOrNull() ?: return@withContext ResetUserPasswordQueryResult(
            status = false,
            message = "User account not found"
        )


        /** Check if otp is valid */
        val isOtpCodeValid = getUser.otpCode == otpCode
        if (!isOtpCodeValid) return@withContext ResetUserPasswordQueryResult(
            status = false,
            message = "Otp code is not valid"
        )


        /** Reset password */
        entries.dbUser.findOneAndUpdate(
            Filters.eq(User::email.name, email),
            listOf(
                Updates.set(User::password.name, encryptionRepository.hashPassword(newPassword)),
                Updates.set(User::otpCode.name, null),
            ),
        ) ?: return@withContext ResetUserPasswordQueryResult(
            httpStatusCode = HttpStatusCode.InternalServerError.value,
            status = false, message = "Failed to reset password"
        )


     /*   *//** Reset otp to null *//*
        launch {
            entries.dbUser.findOneAndUpdate(
                User::email eq email,
                setValue(User::otpCode, null),
            )
        }*/


        return@withContext ResetUserPasswordQueryResult(status = true, message = "Password reset was successful")
    }
}