package com.example.data.database.entries.user

import com.example.data.repository.EncryptionRepositoryImpl

fun User.encryptPassword():User{
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phone = phone,
        password = EncryptionRepositoryImpl().hashPassword(password = password)
    )
}